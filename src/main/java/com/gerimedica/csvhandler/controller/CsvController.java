package com.gerimedica.csvhandler.controller;

import com.gerimedica.csvhandler.model.CsvRecord;
import com.gerimedica.csvhandler.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        csvService.uploadCsv(file);
        return ResponseEntity.ok("File uploaded and data stored successfully");
    }

    @GetMapping("/records")
    public ResponseEntity<List<CsvRecord>> getAllRecords() {
        return ResponseEntity.ok(csvService.getAllRecords());
    }

    @GetMapping("/records/{code}")
    public ResponseEntity<CsvRecord> getRecordByCode(@PathVariable String code) {
        return ResponseEntity.ok(csvService.getRecordByCode(code));
    }


    @DeleteMapping("/records")
    public ResponseEntity<String> deleteAllRecords() {
        csvService.deleteAllRecords();
        return ResponseEntity.ok("All records deleted successfully");
    }
}

