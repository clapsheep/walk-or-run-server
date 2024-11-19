package com.wor.dash.record.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RecordService {
    void uploadCsvFile(MultipartFile file) throws IOException;

}
