package com.gerimedica.csvhandler.service;

import com.gerimedica.csvhandler.repository.CsvRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import static org.mockito.Mockito.*;

public class CsvServiceTest {

    @Mock
    private CsvRecordRepository recordRepository;

    @InjectMocks
    private CsvService csvService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadCsv() throws Exception {
        String csvContent = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n"
                + "Source1,CodeList1,C1,Display1,LongDesc1,2023-01-01,2023-12-31,1\n"
                + "Source2,CodeList2,C2,Display2,LongDesc2,2023-01-01,2023-12-31,2";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        csvService.uploadCsv(file);
        verify(recordRepository, times(1)).saveAll(anyList());
    }

}

