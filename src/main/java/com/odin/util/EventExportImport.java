package com.odin.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.odin.model.Event;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

@Component
public class EventExportImport {
    public void exportToCsv(List<Event> events, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"Summary", "Start Date", "End Date", "Location", "Description"});

            for (Event event : events) {
                writer.writeNext(new String[]{
                    event.getSummary(),
                    event.getStartDateTime().toString(),
                    event.getEndDateTime().toString(),
                    event.getLocation(),
                    event.getDescription()
                });
            }
        }
    }
    
    public List<Event> importFromCsv(String filePath) throws IOException, CsvValidationException {
        List<Event> events = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.readNext();
            
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    Event event = new Event(
                        line[0], // summary
                        LocalDateTime.parse(line[1]), // start
                        LocalDateTime.parse(line[2]), // end
                        line[3], // location 
                        line[4], // description
                        UUID.randomUUID().toString()
                    );
                    events.add(event);
                } catch (Exception e) {
                    // Log the error but continue processing other lines
                    System.err.println("Error processing CSV line: " + String.join(",", line));
                    e.printStackTrace();
                }
            }
        }
        return events;
    }
}