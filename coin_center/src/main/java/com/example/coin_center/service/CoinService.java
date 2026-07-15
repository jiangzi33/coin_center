package com.example.coin_center.service;

import com.example.coin_center.controller.cmd.AddCoinCmd;
import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.entity.Coin;

import java.util.List;

public interface CoinService {
    void add(AddCoinCmd cmd);
    Coin queryById(int id);
    Coin queryByCode(String code);
    List<Coin> queryAll(int start, int pageSize);
    void modify(UpdateCoinCmd cmd);
    void delete(int id);
}
