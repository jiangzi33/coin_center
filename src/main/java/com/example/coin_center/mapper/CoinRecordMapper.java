package com.example.coin_center.mapper;

import com.example.coin_center.entity.CoinRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CoinRecordMapper {
    void insert(CoinRecord coinRecord);
    List<CoinRecord> queryByTime(int start, int pageSize, Date startTime, Date endTime);
    void delete(int id);
}
