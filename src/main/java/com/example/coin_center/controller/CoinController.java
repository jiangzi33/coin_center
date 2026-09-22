package com.example.coin_center.controller;

import com.example.coin_center.controller.cmd.AddCoinCmd;
import com.example.coin_center.controller.cmd.UpdateCoinCmd;
import com.example.coin_center.controller.converter.CoinVOConverter;
import com.example.coin_center.controller.vo.BaseVO;
import com.example.coin_center.controller.vo.CoinVO;
import com.example.coin_center.controller.vo.MultiCoinVO;
import com.example.coin_center.controller.vo.SingleCoinVO;
import com.example.coin_center.entity.Coin;
import com.example.coin_center.exception.CoinNotEnoughException;
import com.example.coin_center.exception.CoinNotExistException;
import com.example.coin_center.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @PostMapping("/add")
    public BaseVO add(@RequestBody AddCoinCmd cmd){
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            coinService.add(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }

    @GetMapping("/queryId")
    public SingleCoinVO queryById(int id){
        long startTime = System.currentTimeMillis();
        long endTime;
        SingleCoinVO singleCoinVO = new SingleCoinVO();
        try {
            Coin coin = coinService.queryById(id);
            CoinVO coinVO = CoinVOConverter.convert(coin);
            endTime = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, endTime - startTime, null);
            singleCoinVO.setCoinVO(coinVO);
            singleCoinVO.setBaseVO(baseVO);
            return singleCoinVO;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
            singleCoinVO.setBaseVO(baseVO);
            return singleCoinVO;
        }
    }

    @GetMapping("/queryCode")
    public SingleCoinVO queryByCode(String code){
        long startTime = System.currentTimeMillis();
        long endTime;
        SingleCoinVO singleCoinVO = new SingleCoinVO();
        try {
            Coin coin = coinService.queryByCode(code);
            CoinVO coinVO = CoinVOConverter.convert(coin);
            endTime = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, endTime - startTime, null);
            singleCoinVO.setCoinVO(coinVO);
            singleCoinVO.setBaseVO(baseVO);
            return singleCoinVO;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
            singleCoinVO.setBaseVO(baseVO);
            return singleCoinVO;
        }
    }

    @GetMapping("/queryAll")
    public MultiCoinVO queryAll(int start, int pageSize){
        long startTime = System.currentTimeMillis();
        long endTime;
        MultiCoinVO multiCoinVO = new MultiCoinVO();
        try {
            List<Coin> coinList = coinService.queryAll(start, pageSize);
            List<CoinVO> coinVOList = CoinVOConverter.convertList(coinList);
            endTime = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, endTime - startTime, null);
            multiCoinVO.setCoinVOList(coinVOList);
            multiCoinVO.setBaseVO(baseVO);
            return multiCoinVO;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
            multiCoinVO.setBaseVO(baseVO);
            return multiCoinVO;
        }
    }

    @PutMapping("/modify")
    public BaseVO modify(UpdateCoinCmd cmd){
        long startTime = System.currentTimeMillis();
        long endTime;
        try{
            coinService.modify(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (CoinNotExistException e){
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, e.getMessage());
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他异常");
        }
    }

    @PutMapping("/decrease-store")
    public BaseVO decreaseStore(String code, int amount,String outBizNo){
        long startTime = System.currentTimeMillis();
        long endTime;
        try{
            coinService.decreaseStore(code,amount,outBizNo);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (CoinNotExistException e){
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, e.getMessage());
        } catch (CoinNotEnoughException e){
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, e.getMessage());
        } catch (DuplicateKeyException e){
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch(Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他异常");
        }
    }

    @DeleteMapping("/delete")
    public BaseVO delete(int id){
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            coinService.delete(id);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }

}
