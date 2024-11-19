package com.wor.dash.record.model.mapper;

import com.wor.dash.record.model.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    void batchInsertRecords(List<Record> batch);
    
} 
