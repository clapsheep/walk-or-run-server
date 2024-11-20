package com.wor.dash.record.model.service;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {
    void uploadCsvFile(MultipartFile file) throws IOException;

    Cadence getCadenceData(int userId, String startTime, String endTime);

    List<Calorie> getCalorieList(int userId, String startTime, String endTime);

    Distance getDistanceData(int userId, String startTime, String endTime);

    List<HeartRate> getHeartRateList(int userId, String startTime, String endTime);

    List<Record> getRecordList(int userId, String startTime, String endTime);

    Speed getSpeedData(int userId, String startTime, String endTime);

    List<Step> getStepList(int userId, String startTime, String endTime);

    List<ExerciseTime> getExerciseTimeList(int userId, String startTime, String endTime);
}
