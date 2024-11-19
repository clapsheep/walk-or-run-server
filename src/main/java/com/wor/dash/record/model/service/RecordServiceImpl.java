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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private static final int BATCH_SIZE = 50;
    private final RecordMapper recordMapper;

    private Integer parseIntOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return (int) Double.parseDouble(value.trim().replace(".0", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDoubleOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void uploadCsvFile(MultipartFile file) throws IOException {
        List<Record> records = new ArrayList<>();
        Map<Integer, Record> orderedRecords = new TreeMap<>();
        AtomicInteger lineCounter = new AtomicInteger(0);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            // Read header
            String header = br.readLine();
            String[] columnNames = header.split(",");
            Map<String, Integer> columnMap = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                columnMap.put(columnNames[i], i);
            }

            // Create thread pool
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Process lines maintaining order
            String line;
            while ((line = br.readLine()) != null) {
                final String currentLine = line;
                final int lineNumber = lineCounter.getAndIncrement();

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        String[] data = currentLine.split(",", -1);
                        Record record = parseRecord(data, columnMap);
                        if (record != null) {
                            synchronized (orderedRecords) {
                                orderedRecords.put(lineNumber, record);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing line " + lineNumber + ": " + currentLine);
                        e.printStackTrace();
                    }
                }, executor);

                futures.add(future);
            }

            // Wait for all parsing to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            executor.shutdown();

            // Convert ordered map to list
            records = new ArrayList<>(orderedRecords.values());
        }

        // Batch insert while maintaining order
        int totalSize = records.size();
        for (int i = 0; i < totalSize; i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, totalSize);
            List<Record> batch = records.subList(i, endIndex);
            recordMapper.batchInsertRecords(batch);
        }
    }

    private Record parseRecord(String[] data, Map<String, Integer> columnMap) {
        try {
            Record record = new Record();
            record.setUserId(2); // 기본값 설정

            // CSV 데이터에서 필요한 필드 추출
            record.setTotalCalorie(parseDoubleOrNull(getValue(data, columnMap, "com.samsung.health.exercise.calorie")));
            record.setDistance(parseDoubleOrNull(getValue(data, columnMap, "com.samsung.health.exercise.distance")));
            record.setMaxHeartRate(parseIntOrNull(getValue(data, columnMap, "com.samsung.health.exercise.max_heart_rate")));
            record.setMinHeartRate(parseIntOrNull(getValue(data, columnMap, "com.samsung.health.exercise.min_heart_rate")));
            record.setMeanHeartRate(parseIntOrNull(getValue(data, columnMap, "com.samsung.health.exercise.mean_heart_rate")));
            record.setCount(parseIntOrNull(getValue(data, columnMap, "com.samsung.health.exercise.count")));
            record.setMeanSpeed(parseDoubleOrNull(getValue(data, columnMap, "com.samsung.health.exercise.mean_speed")));
            record.setMaxSpeed(parseDoubleOrNull(getValue(data, columnMap, "com.samsung.health.exercise.max_speed")));

            // 시간 데이터 처리
            String startTime = getValue(data, columnMap, "com.samsung.health.exercise.start_time");
            String endTime = getValue(data, columnMap, "com.samsung.health.exercise.end_time");
            record.setStartTime(startTime);
            record.setEndTime(endTime);

//            System.out.println("최대속도 ============> "+record.getMaxSpeed());

            return record;
        } catch (Exception e) {
            System.err.println("Error parsing record");
            e.printStackTrace();
            return null;
        }
    }

    private String getValue(String[] data, Map<String, Integer> columnMap, String columnName) {
        Integer index = columnMap.get(columnName);
        if (index != null && index < data.length) {
            return data[index];
        }
        return null;
    }
}