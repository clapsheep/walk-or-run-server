package com.wor.dash.csv.model.service;

import com.wor.dash.record.model.*;
import com.wor.dash.record.model.mapper.RecordMapper;
import com.wor.dash.record.model.*;
import com.wor.dash.record.model.service.SpeedService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class CsvServiceImpl implements CsvService {

    private final RecordMapper recordMapper;

    public CsvServiceImpl(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    @Override
    public void uploadCsvFile(MultipartFile file) throws IOException {
//        Calorie calorie;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // 헤더 읽고 컬럼명 가져오기
            String tmp = br.readLine();
            String[] columnNames = br.readLine().split(",");

            System.out.println("-----------------------");
            System.out.println("컬럼명들 : "+ Arrays.toString(columnNames));
            System.out.println("-----------------------");

            String line;
            int idx = 0;
            while ((line = br.readLine()) != null) {

//                calorie =  new Calorie();

                System.out.println("순서: "+ idx++);
                String[] data = line.split(",", -1);
                System.out.println(Arrays.toString(data));

                Map<String, String> dataMap = new HashMap<>();
                System.out.println(dataMap);
                for (int i = 0; i < data.length; i++) {
                    dataMap.put(columnNames[i], data[i].trim());
                }
                System.out.println(dataMap);
                // Map에서 필요한 데이터 추출하여 User 객체에 설정
                try {
                    System.out.println("들어온다");
                    System.out.println("============> "+dataMap.getOrDefault("total_calorie", null));
                    System.out.println("============> "+dataMap.getOrDefault("heart_rate_sample_count", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.mean_heart_rate", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.max_heart_rate", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.max_speed", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.min_heart_rate", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.count", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.distance", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.calorie", null));
                    System.out.println("============> "+dataMap.getOrDefault("com.samsung.health.exercise.mean_speed", null));

//                    calorie.setTotalCalorie(Double.parseDouble(dataMap.getOrDefault("total_calorie", "0")));
//                    Time time = new Time();
//// time 데이터 설정
//                    recordMapper.insertTime(time);
//                    calorie.setTimeId(time.getTimeId());
                    
//                    recordMapper.insertCalorie(calorie);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
