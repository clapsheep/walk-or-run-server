package com.wor.dash.record.model.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class RecordServiceImpl implements RecordService {

    @Override
    public void uploadCsvFile(MultipartFile file) throws IOException {
//        String csvFile = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                String value = csvRecord.get(32); // 첫 번째 열의 값 가져오기
                System.out.println("Value: " + value);
            }

            csvParser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//package com.wor.dash.record.model.service;
//
//import com.wor.dash.record.model.Record;
//import com.wor.dash.record.model.mapper.RecordMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class RecordServiceImpl3 implements RecordService {
//    private static final int BATCH_SIZE = 50;  // 주석 해제
//    private final RecordMapper recordMapper;
//
//    // 유틸리티 메서드 추가
//    private Integer parseIntOrNull(String value) {
//        if (value == null || value.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            return Integer.parseInt(value.trim());
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//
//    private Double parseDoubleOrNull(String value) {
//        if (value == null || value.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            return Double.parseDouble(value.trim());
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//
//    @Override
//    @Transactional
//    public void uploadCsvFile(MultipartFile file) throws IOException {
//        List<Record> records = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            br.readLine();
//            String[] columnNames = br.readLine().split(",");
//
//            // CSV에서 읽어온 문자열 형식의 날짜 데이터를 java.util.Date 객체로 변환
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
//
//
//            // java.util.Date 객체를 MYSQL의 DATETIME 형식으로 변환
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/// /            String mysqlDateString = outputFormat.format(dateObject);
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",", -1);
//                System.out.println("해당열의 전체 데이터 배열: " + Arrays.toString(data));
//
//                Map<String, String> dataMap = new HashMap<>();
//                for (int i = 0; i < data.length; i++) {
//                    dataMap.put(columnNames[i], data[i]);
//                }
//                System.out.println("맵에 담기 성공");
//
//                try {
//                    Record record = new Record();
//                    record.setUserId(1);
//                    record.setTotalCalorie(parseIntOrNull(dataMap.get("total_calorie")));
//                    System.out.println("칼로리: " + record.getTotalCalorie());
/// /                    record.setDuration(parseIntOrNull(dataMap.get("com.samsung.health.exercise.duration")));
/// /                    Date dateObject = inputFormat.parse(dataMap.get("com.samsung.health.exercise.start_time"));
/// /                    record.setStartTime(dateObject);
//                    System.out.println(dataMap.get("com.samsung.health.exercise.start_time"));
//                    System.out.println("시간: " + record.getStartTime());
//                    record.setMeanHeartRate(parseIntOrNull(dataMap.get("com.samsung.health.exercise.mean_heart_rate")));
//                    System.out.println("심박수 평균: " + record.getMeanHeartRate());
//                    record.setMaxHeartRate(parseIntOrNull(dataMap.get("com.samsung.health.exercise.max_heart_rate")));
//                    record.setMaxSpeed(parseIntOrNull(dataMap.get("com.samsung.health.exercise.max_speed")));
//                    record.setMinHeartRate(parseIntOrNull(dataMap.get("com.samsung.health.exercise.min_heart_rate")));
//                    record.setCount(parseIntOrNull(dataMap.get("com.samsung.health.exercise.count")));
//                    record.setDistance(parseDoubleOrNull(dataMap.get("com.samsung.health.exercise.distance")));
//                    record.setMeanSpeed(parseDoubleOrNull(dataMap.get("com.samsung.health.exercise.mean_speed")));
/// /                    record.setEndTime(dataMap.get("com.samsung.health.exercise.end_time"));
//
//                    records.add(record);
//                    System.out.println("리스트에 담기 성공");
//                    System.out.println("종료시간=============" + record.getEndTime());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // 배치 처리
//            int totalSize = records.size();
//            for (int i = 0; i < totalSize; i += BATCH_SIZE) {
//                int endIndex = Math.min(i + BATCH_SIZE, totalSize);
//                List<Record> batch = records.subList(i, endIndex);
//                System.out.println(batch.get(0));
//                recordMapper.batchInsertRecords(batch);
//                System.out.println("매퍼전달성공");
//                System.out.println(batch);
//            }
//        }
//    }
//}
