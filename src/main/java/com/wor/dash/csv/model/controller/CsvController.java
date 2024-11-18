package com.wor.dash.csv.model.controller;

import com.wor.dash.csv.model.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/csv")
public class CsvController {

    @Autowired
    private CsvService csvService;

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("csv") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 비어있습니다.");
        }

        System.out.println();
        try {
            csvService.uploadCsvFile(file);
            return ResponseEntity.ok("CSV 파일 업로드 성공: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSV 파일 업로드 실패: " + e.getMessage());
        }
    }

}
