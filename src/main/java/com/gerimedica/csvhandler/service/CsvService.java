package com.gerimedica.csvhandler.service;

import com.gerimedica.csvhandler.model.CsvRecord;
import com.gerimedica.csvhandler.repository.CsvRecordRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvService {

    private final CsvRecordRepository csvRecordRepository;

    public CsvService(CsvRecordRepository csvRecordRepository) {
        this.csvRecordRepository = csvRecordRepository;
    }

    public void uploadCsv(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            log.info("Processing uploaded CSV file.");
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            List<CsvRecord> records = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                CsvRecord record = new CsvRecord(
                        csvRecord.get("source"),
                        csvRecord.get("codeListCode"),
                        csvRecord.get("code"),
                        csvRecord.get("displayValue"),
                        csvRecord.get("longDescription"),
                        csvRecord.get("fromDate"),
                        csvRecord.get("toDate"),
                        csvRecord.get("sortingPriority")
                );

                records.add(record);
            }
            csvRecordRepository.saveAll(records);
            log.info("Saved {} records to the database.", records.size());
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file", e);
        }
    }



    public List<CsvRecord> getAllRecords() {
        log.info("Fetching all records from the repository.");
        return csvRecordRepository.findAll();
    }

    public CsvRecord getRecordByCode(String code) {
        log.info("Fetching record with code: {}", code);
        return csvRecordRepository.findById(code).orElseThrow(() -> new RuntimeException("Record not found"));
    }


    public void deleteAllRecords() {
        csvRecordRepository.deleteAll();
        log.info("All records have been deleted.");
    }
}