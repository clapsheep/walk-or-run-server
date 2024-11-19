package com.wor.dash.record.controller;

import com.wor.dash.record.model.service.RecordService;
import com.wor.dash.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/upload-csv")
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


}
