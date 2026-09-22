package com.example.coin_center.controller.converter;

import com.example.coin_center.controller.vo.CoinVO;
import com.example.coin_center.entity.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinVOConverter {

    public static CoinVO convert(Coin coin) {
        if (coin == null) {
            return null;
        }

        CoinVO coinVO = new CoinVO();
        coinVO.setId(coin.getId());
        coinVO.setCode(coin.getCode());
        coinVO.setPrice(coin.getPrice());
        coinVO.setStore(coin.getStore());
        coinVO.setLastOperationTime(coin.getLastOperationTime());

        return coinVO;
    }

    public static List<CoinVO> convertList(List<Coin> coinList) {
        List<CoinVO> coinVOList = new ArrayList<>();

        if (coinList == null || coinList.isEmpty()) {
            return coinVOList;
        }

        for (Coin coin : coinList) {
            coinVOList.add(convert(coin));
        }

        return coinVOList;
    }
}
