package com.example.coin_center.controller.converter;

import com.example.coin_center.controller.vo.CoinRecordVO;
import com.example.coin_center.controller.vo.CoinVO;
import com.example.coin_center.entity.CoinRecord;

import java.util.ArrayList;
import java.util.List;

public class CoinRecordVOConverter {

    public static CoinRecordVO convert(CoinRecord coinRecord) {
        if (coinRecord == null) {
            return null;
        }

        CoinRecordVO coinRecordVO = new CoinRecordVO();
        coinRecordVO.setId(coinRecord.getId());
        coinRecordVO.setCode(coinRecord.getCode());
        coinRecordVO.setAmount(coinRecordVO.getAmount());
        coinRecordVO.setOutBizNo(coinRecord.getOutBizNo());
        coinRecordVO.setPrizeTime(coinRecord.getPrizeTime());

        return coinRecordVO;
    }

    public static List<CoinRecordVO> convertList(List<CoinRecord> coinRecordList) {
        List<CoinRecordVO> coinRecordVOList = new ArrayList<>();

        if (coinRecordList == null || coinRecordList.isEmpty()) {
            return coinRecordVOList;
        }

        for (CoinRecord coinRecord : coinRecordList) {
            coinRecordVOList.add(convert(coinRecord));
        }

        return coinRecordVOList;
    }
}
