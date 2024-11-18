package com.wor.dash.csv.model.service;

import com.wor.dash.record.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvService {

    void uploadCsvFile(MultipartFile file) throws IOException;

}
