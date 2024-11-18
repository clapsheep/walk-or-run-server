package com.wor.dash.record.model.mapper;

import com.wor.dash.record.model.Calorie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper {
    int insertCalorie(Calorie calorie);

} 
