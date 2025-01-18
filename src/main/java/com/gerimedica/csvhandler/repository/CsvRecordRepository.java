package com.gerimedica.csvhandler.repository;

import com.gerimedica.csvhandler.model.CsvRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRecordRepository extends JpaRepository<CsvRecord, String> {
}