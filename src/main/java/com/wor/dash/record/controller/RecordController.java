package com.wor.dash.record.controller;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import com.wor.dash.record.model.service.RecordService;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}")
@RequiredArgsConstructor
@Tag(name = "Record", description = "운동 기록 관리 API")
public class RecordController {
    private final RecordService recordService;

    // csv 업로드
    @PostMapping("/record/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("csv") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("fail", "FileEmpty", 400), HttpStatus.BAD_REQUEST);
        }

        try {
            recordService.uploadCsvFile(file);

            return new ResponseEntity<>(new ApiResponse("success", "File Upload Success", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "File Upload Failed", 400), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 심박수, 차트, 주간데이터
    @GetMapping("/record/heartRate")
    public ResponseEntity<?> getHeartRateData(@PathVariable("userId") int userId) {
        try {
            List<HeartRate> result = recordService.getHeartRateData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 운동중 걸음수, 차트, 주간 데이터
    @GetMapping("/record/step")
    public ResponseEntity<?> getStepData(@PathVariable("userId") int userId) {
        try {
            List<Step> result = recordService.getStepData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 칼로리, 차트, 주간데이터
    @GetMapping("/record/calorie")
    public ResponseEntity<?> getCalorieData(@PathVariable("userId") int userId) {
        try {
            List<Calorie> result = recordService.getCalorieData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get calorie data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 케이던스 = (총 걸음수) / (총 걸린시간, 분), 수치, 일간데이터
    @GetMapping("/record/cadence")
    public ResponseEntity<?> getCadenceData(@PathVariable("userId") int userId) {
        try {
            Cadence result = recordService.getCadenceData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 누적거리(m), 수치, 일간데이터
    @GetMapping("/record/distance")
    public ResponseEntity<?> getDistanceData(@PathVariable("userId") int userId) {
        try {
            Distance result = recordService.getDistanceData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 주간 평균 속도(m/s), 수치, 일간 데이터
    @GetMapping("/record/speed")
    public ResponseEntity<?> getSpeedData(@PathVariable("userId") int userId) {
        try {
            Speed result = recordService.getSpeedData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 모든 데이터
    @GetMapping("/record/record")
    public ResponseEntity<?> getRecordData(@PathVariable("userId") int userId) {
        try {
            List<Record> result = recordService.getRecordData(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}
