package com.example.coin_center.mapper;

import com.example.coin_center.entity.Coin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoinMapper {
    void add(Coin coin);
    Coin queryById(int id);
    Coin queryByCode(String code);
    List<Coin> queryAll(int start, int pageSize);
    void modify(Coin coin);
    void delete(int id);
}
