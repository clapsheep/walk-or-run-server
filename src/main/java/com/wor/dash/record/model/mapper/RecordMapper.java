package com.wor.dash.record.model.mapper;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    void batchInsertRecords(List<Record> batch);

    Cadence getCadenceData(int userId);

    List<Calorie> getCalorieData(int userId);

    Distance getDistanceData(int userId);

    List<HeartRate> getHeartRateData(int userId);

    List<Record> getRecordData(int userId);

    Speed getSpeedData(int userId);

    List<Step> getStepData(int userId);
} 
