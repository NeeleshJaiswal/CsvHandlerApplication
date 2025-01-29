package com.gerimedica.csvhandler.controller;

import com.gerimedica.csvhandler.model.CsvRecord;
import com.gerimedica.csvhandler.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        log.info("Received CSV upload request: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            log.warn("Uploaded file is empty.");
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid CSV file.");
        }

        if (!Objects.requireNonNull(file.getContentType()).equals("text/csv")) {
            log.warn("Invalid file format: {}", file.getContentType());
            return ResponseEntity.badRequest().body("Invalid file format. Please upload a CSV file.");
        }

        csvService.uploadCsv(file);
        log.info("CSV file uploaded and processed successfully.");
        return ResponseEntity.ok("File uploaded and data stored successfully");
    }

    @GetMapping("/records")
    public ResponseEntity<List<CsvRecordDto>> getAllRecords() {
        List<CsvRecordDto> records = csvService.getAllRecords()
            .stream()
            .map(CsvRecordDto::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(records);
    }

    @GetMapping("/records/{code}")
    public ResponseEntity<CsvRecordDto> getRecordByCode(@PathVariable String code) {
        Optional<CsvRecord> record = csvService.getRecordByCode(code);
        return record.map(csvRecord -> ResponseEntity.ok(new CsvRecordDto(csvRecord)))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/records")
    public ResponseEntity<String> deleteAllRecords() {
        boolean deleted = csvService.deleteAllRecords();

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No records found to delete.");
        }

        return ResponseEntity.ok("All records deleted successfully");
    }
}

