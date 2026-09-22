package com.example.coin_center.controller.vo;

import java.util.List;

public class MultiCoinVO {
    private BaseVO baseVO;
    private List<CoinVO> coinVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<CoinVO> getCoinVOList() {
        return coinVOList;
    }

    public void setCoinVOList(List<CoinVO> coinVOList) {
        this.coinVOList = coinVOList;
    }
}
