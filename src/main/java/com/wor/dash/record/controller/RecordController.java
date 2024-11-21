package com.wor.dash.record.controller;

import com.wor.dash.record.model.Record;
import com.wor.dash.record.model.*;
import com.wor.dash.record.model.service.RecordService;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Record", description = "대시보드 파일 입출력 기능을 관리합니다.")
public class RecordController {
    private final RecordService recordService;

    // csv 업로드
    @Operation(summary = "파일 업로드", description = "챌린지를 수정하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n")
    @PostMapping("/record/upload")
    public ResponseEntity<?> uploadCsvFile(@RequestParam("csv") MultipartFile file,
                                           @PathVariable("userId") int userId) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("fail", "FileEmpty", 400), HttpStatus.BAD_REQUEST);
        }

        try {
            recordService.uploadCsvFile(file, userId); // 유저아이디 전달 기능 아직 구현안했어!!!

            return new ResponseEntity<>(new ApiResponse("success", "File Upload Success", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail", "File Upload Failed", 400), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 심박수, 차트, 주간데이터
    @Operation(summary = "심박수 조회(최대, 최소, 평균)", description = "심박수를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/heartRate")
    public ResponseEntity<?> getHeartRateData(@PathVariable("userId") int userId,
                                              @RequestParam("startTime") String startTime,
                                              @RequestParam("endTime") String endTime) {
        try {
            List<HeartRate> result = recordService.getHeartRateList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 운동중 걸음수, 차트, 주간 데이터
    @Operation(summary = "운동중 걸음수 조회(최대, 최소, 평균)", description = "운동중 걸음수를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/step")
    public ResponseEntity<?> getStepData(@PathVariable("userId") int userId,
                                         @RequestParam("startTime") String startTime,
                                         @RequestParam("endTime") String endTime) {
        try {
            List<Step> result = recordService.getStepList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 칼로리, 차트, 주간데이터
    @Operation(summary = "하루 섭취 칼로리 조회", description = "하루 중 섭취한 칼로리를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/calorie")
    public ResponseEntity<?> getCalorieData(@PathVariable("userId") int userId,
                                            @RequestParam("startTime") String startTime,
                                            @RequestParam("endTime") String endTime) {
        try {
            List<Calorie> result = recordService.getCalorieList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get calorie data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 케이던스 = (총 걸음수) / (총 걸린시간, 분), 수치, 일간데이터
    @Operation(summary = "케이던스 조회", description = "케이던스를 조회하기 위한 API \n\n " +
            "케이던스 : 분 당 걸음수 (총 걸음수 / 총 걸린 시간(분))\n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/cadence")
    public ResponseEntity<?> getCadenceData(@PathVariable("userId") int userId,
                                            @RequestParam("startTime") String startTime,
                                            @RequestParam("endTime") String endTime) {
        try {
            List<Cadence> result = recordService.getCadenceList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 누적거리(m), 수치, 일간데이터
    @Operation(summary = "누적 이동 거리 조회 (m)", description = "하루동안 이동한 거리를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/distance")
    public ResponseEntity<?> getDistanceData(@PathVariable("userId") int userId,
                                             @RequestParam("startTime") String startTime,
                                             @RequestParam("endTime") String endTime) {
        try {
            List<Distance> result = recordService.getDistanceList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 주간 평균 속도(m/s), 수치, 일간 데이터
    @Operation(summary = "속도 조회 (m/s)", description = "속도를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/speed")
    public ResponseEntity<?> getSpeedData(@PathVariable("userId") int userId,
                                          @RequestParam("startTime") String startTime,
                                          @RequestParam("endTime") String endTime) {
        try {
            List<Speed> result = recordService.getSpeedList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 모든 데이터
    @Operation(summary = "모든 데이터 조회", description = "모든 데이터를 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/record")
    public ResponseEntity<?> getRecordData(@PathVariable("userId") int userId,
                                           @RequestParam("startTime") String startTime,
                                           @RequestParam("endTime") String endTime) {
        try {
            List<Record> result = recordService.getRecordList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 운동 시간 통계 : 기간 내의 start_time, end_time 데이터를 전부 전달, 이후 데이터 조작은 프론트에서 구현
    @Operation(summary = "운동 시간 조회 (min)", description = "하루 중 운동한 시간을 조회하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- userId : 로그인한 유저ID \n" +
            "### query \n" +
            "- startTime : 시작 날짜 (ex.2024-07-01, 해당 날짜의 00:00:00부터 조회함) \n " +
            "- endTime : 종료 날짜 (ex.2024-07-01, 해당 날짜의 23:59:59까지 조회함) \n")
    @GetMapping("/record/exerciseTime")
    public ResponseEntity<?> getExerciseTimeList(@PathVariable("userId") int userId,
                                                 @RequestParam("startTime") String startTime,
                                                 @RequestParam("endTime") String endTime) {
        try {
            List<ExerciseTime> result = recordService.getExerciseTimeList(userId, startTime, endTime);
            if (result.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse("empty", "No data available for this user", 204),
                        HttpStatus.NO_CONTENT
                );
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse("fail", "Failed to get distance data", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}