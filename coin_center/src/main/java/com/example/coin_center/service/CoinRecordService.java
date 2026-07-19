package com.example.coin_center.service;

import com.example.coin_center.entity.CoinRecord;

import java.util.Date;
import java.util.List;

public interface CoinRecordService {
    void insert(CoinRecord coinRecord);
    List<CoinRecord> queryByTime(int start, int pageSize, Date startTime, Date endTime);
    void delete(int id);
}
