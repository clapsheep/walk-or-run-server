package com.wor.dash.record.model.service;

import com.wor.dash.record.model.Record;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {
    List<Record> uploadCsvFile(MultipartFile file) throws IOException;

}
