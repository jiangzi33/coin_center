package com.example.coin_center.controller;

import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.controller.converter.CoinRecordVOConverter;
import com.example.coin_center.controller.converter.CoinVOConverter;
import com.example.coin_center.controller.vo.*;
import com.example.coin_center.entity.Coin;
import com.example.coin_center.entity.CoinRecord;
import com.example.coin_center.exception.CoinNotEnoughException;
import com.example.coin_center.exception.CoinNotExistException;
import com.example.coin_center.service.CoinRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coin-record")
@Slf4j
public class CoinRecordController {
    @Autowired
    private CoinRecordService coinRecordService;

    @GetMapping("/queryTime")
    public MultiCoinRecordVO queryByTime(int start, int pageSize, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime){
        long start_Time = System.currentTimeMillis();
        long end_Time;
        MultiCoinRecordVO multiCoinRecordVO = new MultiCoinRecordVO();
        try {
            List<CoinRecord> coinRecords = coinRecordService.queryByTime(start, pageSize, startTime, endTime);
            List<CoinRecordVO> coinRecordVOS = CoinRecordVOConverter.convertList(coinRecords);
            end_Time = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, end_Time - start_Time, null);
            multiCoinRecordVO.setCoinRecordVOList(coinRecordVOS);
            multiCoinRecordVO.setBaseVO(baseVO);
            return multiCoinRecordVO;
        } catch (Exception e) {
            end_Time = System.currentTimeMillis();
            log.error(e.getMessage());
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, end_Time - start_Time, "其他未知异常");
            multiCoinRecordVO.setBaseVO(baseVO);
            return multiCoinRecordVO;
        }
    }

    @DeleteMapping("/delete")
    public BaseVO delete(int id){
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            coinRecordService.delete(id);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }
}
