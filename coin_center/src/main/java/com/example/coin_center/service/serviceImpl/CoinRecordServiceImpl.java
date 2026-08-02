package com.example.coin_center.service.serviceImpl;

import com.example.coin_center.entity.CoinRecord;
import com.example.coin_center.mapper.CoinRecordMapper;
import com.example.coin_center.service.CoinRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CoinRecordServiceImpl implements CoinRecordService {
    @Autowired
    private CoinRecordMapper coinRecordMapper;
    @Override
    public void insert(CoinRecord coinRecord) {
        coinRecordMapper.insert(coinRecord);
    }

    @Override
    public List<CoinRecord> queryByTime(int start, int pageSize, Date startTime, Date endTime) {
        return coinRecordMapper.queryByTime(start,pageSize,startTime,endTime);
    }

    @Override
    public void delete(int id) {
        coinRecordMapper.delete(id);
    }
}
