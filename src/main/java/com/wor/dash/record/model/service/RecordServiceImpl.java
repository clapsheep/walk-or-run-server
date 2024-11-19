package com.wor.dash.record.model.service;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.mapper.RecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private static final int BATCH_SIZE = 50;
    private final RecordMapper recordMapper;

    @Override
    @Transactional
    public List<Record> uploadCsvFile(MultipartFile file) throws IOException {
        List<Record> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // 헤더 읽고 컬럼명 가져오기
            br.readLine();
            String[] columnNames = br.readLine().split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                System.out.println(Arrays.toString(data));

                Map<String, String> dataMap = new HashMap<>();
                for (int i = 0; i < data.length; i++) {
                    dataMap.put(columnNames[i], data[i].trim());
                }

                try {
                    Record record = new Record();
                    record.setTotalCalorie(Integer.parseInt(dataMap.getOrDefault("total_calorie", null)));
                    record.setDuration(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.duration", null)));
                    record.setStartTime(dataMap.getOrDefault("com.samsung.health.exercise.start_time", null));
                    record.setMeanHeartRate(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.mean_heart_rate", null)));
                    record.setMaxHeartRate(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.max_heart_rate", null)));
                    record.setMaxSpeed(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.max_speed", null)));
                    record.setMinHeartRate(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.min_heart_rate", null)));
                    record.setCount(Integer.parseInt(dataMap.getOrDefault("com.samsung.health.exercise.count", null)));
                    record.setDistance(Double.parseDouble(dataMap.getOrDefault("com.samsung.health.exercise.distance", null)));
                    record.setMeanSpeed(dataMap.getOrDefault("com.samsung.health.exercise.mean_speed", null));
                    record.setEndTime(dataMap.getOrDefault("com.samsung.health.exercise.end_time", null));

                    records.add(record);

                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error processing record: " + e.getMessage());
                }
            }

            // 배치 처리
            int totalSize = records.size();
            for (int i = 0; i < totalSize; i += BATCH_SIZE) {
                int endIndex = Math.min(i + BATCH_SIZE, totalSize);
                List<Record> batch = records.subList(i, endIndex);
                recordMapper.batchInsertRecords(batch);
            }

            return records;
        }
    }
}
