package com.wor.dash.record.model.service;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {
    void uploadCsvFile(MultipartFile file) throws IOException;

    Cadence getCadenceData(int userId);

    List<Calorie> getCalorieData(int userId);

    Distance getDistanceData(int userId);

    List<HeartRate> getHeartRateData(int userId);

    List<Record> getRecordData(int userId);

    Speed getSpeedData(int userId);

    List<Step> getStepData(int userId);
}
