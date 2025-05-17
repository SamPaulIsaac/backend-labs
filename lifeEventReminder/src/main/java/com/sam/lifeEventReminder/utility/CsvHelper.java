package com.sam.lifeEventReminder.utility;


import com.sam.lifeEventReminder.entity.Event;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvHelper {

    public List<Event> parse(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            // Use the new static parse method of CSVParser
            CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            List<Event> events = new ArrayList<>();

            // Iterate through CSV records
            for (CSVRecord csvRecord : csvParser) {
                Event event = new Event();

                // Parse individual fields, ensure CSV header names are correct
                event.setTitle(csvRecord.get("title"));
                event.setEventDate(LocalDate.parse(csvRecord.get("event_date")));  // Handle potential parsing exceptions
                event.setRecipients(csvRecord.get("recipients"));

                // Enum parsing, ensure case matches in CSV
                try {
                    event.setChannel(Channel.valueOf(csvRecord.get("channel").toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid channel value in CSV: " + csvRecord.get("channel"), e);
                }

                events.add(event);
            }

            return events;

        } catch (IllegalArgumentException | IOException e) {
            // Log exception and rethrow as a runtime exception
            throw new RuntimeException("Failed to parse CSV file", e);
        }
    }
}
