package com.example.coin_center.service.serviceImpl;

import com.example.coin_center.controller.cmd.AddCoinCmd;
import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.entity.Coin;
import com.example.coin_center.entity.CoinRecord;
import com.example.coin_center.exception.CoinNotEnoughException;
import com.example.coin_center.exception.CoinNotExistException;
import com.example.coin_center.mapper.CoinMapper;
import com.example.coin_center.service.CoinRecordService;
import com.example.coin_center.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinRecordService coinRecordService;
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
        Coin coin = coinMapper.queryByCode(code);
        if(coin==null){
            throw new CoinNotExistException("coin not existed");
        }
        if(amount>coin.getStore()){
            throw new CoinNotEnoughException("coin not enough");
        }
        coin.setStore(coin.getStore()-amount);
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
