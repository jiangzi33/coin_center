package com.example.coin_center.service.serviceImpl;

import com.example.coin_center.controller.cmd.AddCoinCmd;
import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.entity.Coin;
import com.example.coin_center.entity.CoinRecord;
import com.example.coin_center.exception.CoinNotEnoughException;
import com.example.coin_center.exception.CoinNotExistException;
import com.example.coin_center.exception.RateLimitExceededException;
import com.example.coin_center.mapper.CoinMapper;
import com.example.coin_center.service.CoinRecordService;
import com.example.coin_center.service.CoinService;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinRecordService coinRecordService;
    @Autowired
    private RedissonClient redissonClient;

    private RRateLimiter rateLimiter;

    @PostConstruct
    public void initRateLimiter() {
        rateLimiter = redissonClient.getRateLimiter("coin:rate_limiter:decreaseStore");
        // 令牌桶: 每秒最多 10 次 decreaseStore 请求（全局）
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
    }
    @Override
    public void add(AddCoinCmd cmd) {
        Coin coin = buildCoin(cmd);
        coinMapper.add(coin);
    }

    @Override
    public Coin queryById(int id) {
        return coinMapper.queryById(id);
    }

    @Override
    public Coin queryByCode(String code) {
        return coinMapper.queryByCode(code);
    }

    @Override
    public List<Coin> queryAll(int start, int pageSize) {
        return coinMapper.queryAll(start,pageSize);
    }

    @Override
    public void modify(UpdateCoinCmd cmd) {
        Coin coin = coinMapper.queryById(cmd.getId());
        if(coin==null){
            throw new CoinNotExistException("coin not existed");
        }
        Coin coin1 = modifyCoin(coin, cmd);
        coinMapper.modify(coin1);
    }
    @Override
    public void delete(int id) {
        coinMapper.delete(id);
    }

    @Override
    @Transactional
    public void decreaseStore(String code, int amount, String outBizNo) {
        // 令牌桶限流: 无可用令牌则直接拒绝
        if (!rateLimiter.tryAcquire()) {
            throw new RateLimitExceededException("request rate limit exceeded, please retry later");
        }

        // 分布式锁: 按 coin code 粒度加锁，防止并发超发
        RLock lock = redissonClient.getLock("coin:lock:" + code);
        boolean locked;
        try {
            // waitTime=0: 抢不到锁立即返回；leaseTime=5s: 防止宕机死锁
            locked = lock.tryLock(0, 5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("acquire lock interrupted");
        }
        if (!locked) {
            throw new RuntimeException("acquire lock failed, please retry");
        }

        // 锁在事务提交后释放，避免"锁已释放但事务未提交"导致其他线程读到旧库存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        });

        Coin coin = coinMapper.queryByCode(code);
        if (coin == null) {
            throw new CoinNotExistException("coin not existed");
        }
        if (amount > coin.getStore()) {
            throw new CoinNotEnoughException("coin not enough");
        }
        coin.setStore(coin.getStore() - amount);
        coinMapper.modify(coin);
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setCode(code);
        coinRecord.setAmount(amount);
        coinRecord.setOutBizNo(outBizNo);
        coinRecordService.insert(coinRecord);
    }

    private Coin buildCoin(AddCoinCmd cmd) {
        Coin coin = new Coin();

        coin.setCode(cmd.getCode());
        coin.setPrice(cmd.getPrice());
        coin.setStore(cmd.getStore());

        return coin;
    }

    private Coin modifyCoin(Coin coin, UpdateCoinCmd cmd){
        coin.setCode(cmd.getCode());
        coin.setPrice(cmd.getPrice());
        coin.setStore(cmd.getStore());
        coin.setId(cmd.getId());

        return coin;
    }
}
