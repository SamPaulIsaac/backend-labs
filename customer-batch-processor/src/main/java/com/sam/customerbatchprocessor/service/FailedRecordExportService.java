package com.sam.customerbatchprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.customerbatchprocessor.entity.FailedRecord;
import com.sam.customerbatchprocessor.repository.FailedRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FailedRecordExportService {

    @Autowired
    private FailedRecordRepository failedRecordRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public void exportToCsv(String fileName) {
        List<FailedRecord> records = failedRecordRepository.findAll();

        try (
                FileWriter writer = new FileWriter(fileName);
                CSVPrinter csvPrinter = new CSVPrinter(
                        writer,
                        CSVFormat.DEFAULT
                        .withHeader("ID", "Original Data", "Error Message", "Job Name", "Job Execution ID", "File Reference", "Failed At"))
        ) {
            for (FailedRecord record : records) {
                String cleanJson;
                try {
                    // Step 1: Remove outer quotes and parse escaped JSON string to Object
                    Object jsonObject = objectMapper.readValue(record.getOriginalData(), Object.class);

                    // Step 2: Write clean, unescaped JSON string
                    cleanJson = objectMapper.writeValueAsString(jsonObject);
                } catch (Exception e) {
                    // Fallback to raw if anything goes wrong
                    cleanJson = record.getOriginalData();
                }

                csvPrinter.printRecord(
                        record.getId(),
                        cleanJson,
                        record.getErrorMessage(),
                        record.getJobName(),
                        record.getJobExecutionId(),
                        record.getFileReference(),
                        record.getFailedAt()
                );
            }

            csvPrinter.flush();
            log.info("Exported {} failed records to {}", records.size(), fileName);

        } catch (IOException e) {
            log.error("Failed to export to CSV", e);
        }
    }


}

