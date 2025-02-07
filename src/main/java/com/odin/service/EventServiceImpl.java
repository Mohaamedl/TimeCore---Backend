package com.odin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.odin.model.Event;
import com.odin.model.User;
import com.odin.repository.EventRepository;
import com.odin.repository.UserRepository;
import com.odin.util.Converter;
import com.odin.util.PDFImporter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Event> importEventsFromPdf(MultipartFile file, User user) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is required");
        }

        log.info("Processing PDF import for user: {}", user.getEmail());
        File tempFile = null;

        try {
            // Create temp file
            tempFile = File.createTempFile("schedule_", ".pdf");
            file.transferTo(tempFile);
            log.debug("Created temp file: {}", tempFile.getAbsolutePath());

            // Read PDF text
            String pdfText = PDFImporter.readPdfText(tempFile.getPath());
            if (pdfText == null || pdfText.isEmpty()) {
                throw new Exception("No text extracted from PDF");
            }
            log.debug("Extracted {} characters from PDF", pdfText.length());

            // Convert to events
            List<Event> events = Converter.convertScheduleToEvents(pdfText);
            if (events.isEmpty()) {
                throw new Exception("No events found in PDF");
            }
            log.info("Found {} events in PDF", events.size());

            // Save events
            List<Event> savedEvents = new ArrayList<>();
            for (Event event : events) {
                try {
                    event.getUsers().add(user);
                    savedEvents.add(saveEvent(event));
                } catch (Exception e) {
                    log.error("Error saving event: {}", e.getMessage());
                }
            }

            log.info("Saved {} events for user {}", savedEvents.size(), user.getEmail());
            return savedEvents;

        } catch (Exception e) {
            log.error("PDF import failed", e);
            throw new Exception("PDF import failed: " + e.getMessage());
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getEventsByUser(Long userId) {
        return eventRepository.findByUsersId(userId);
    }

    @Override
    public Event addUserToEvent(Long eventId, Long userId) throws Exception {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new Exception("Event not found"));
            
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new Exception("User not found"));
            
        event.getUsers().add(user);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> importEventsFromCSV(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}