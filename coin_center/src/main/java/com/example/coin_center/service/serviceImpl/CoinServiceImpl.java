package com.example.coin_center.service.serviceImpl;

import com.example.coin_center.controller.cmd.AddCoinCmd;
import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.entity.Coin;
import com.example.coin_center.exception.CoinNotExistException;
import com.example.coin_center.mapper.CoinMapper;
import com.example.coin_center.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinMapper coinMapper;
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
