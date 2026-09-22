package com.example.coin_center.controller.vo;

import java.util.List;

public class MultiCoinRecordVO {
    private BaseVO baseVO;
    List<CoinRecordVO> coinRecordVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<CoinRecordVO> getCoinRecordVOList() {
        return coinRecordVOList;
    }

    public void setCoinRecordVOList(List<CoinRecordVO> coinRecordVOList) {
        this.coinRecordVOList = coinRecordVOList;
    }
}
