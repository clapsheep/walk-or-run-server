package com.wor.dash.record.model.mapper;

import com.wor.dash.record.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    int insertCalorie(List<Calorie> users);

    int insertDistance(List<Distance> distances);

    int insertHeartRate(List<HeartRate> heartRates);

    int insertSpeed(List<Speed> speeds);

    int insertStep(List<Step> steps);

    int insertTime(List<Time> times);

} 
