package com.wor.dash.record.model.service;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {
    void uploadCsvFile(MultipartFile file) throws IOException;

    List<Calorie> getCalorieData(int UserId);

    List<Distance> getDistanceDate(int UserId);

    List<HeartRate> getHeartRate(int UserId);

    List<Record> getRecord(int UserId);

    List<Speed> getSpeed(int UserId);

    List<Step> getStepData(int UserId);
}
