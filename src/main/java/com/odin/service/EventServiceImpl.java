package com.odin.service;

import java.io.File;
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

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

/*    @Override
    public List<Event> importEventsFromPdf(MultipartFile file) throws Exception {
        // Create temp file
        File tempFile = File.createTempFile("schedule", ".pdf");
        file.transferTo(tempFile);

        // Extract text from PDF
        String pdfText = PDFImporter.readPdfText(tempFile.getPath());
        
        // Convert text to events
        List<Event> events = Converter.convertScheduleToEvents(pdfText);
        
        // Save events to database
        events.forEach(this::saveEvent);
        
        // Cleanup
        tempFile.delete();
        
        return events;
    }*/

    @Override
    public List<Event> importEventsFromPdf(MultipartFile file, User user) throws Exception {
        // Create temp file
        File tempFile = File.createTempFile("schedule", ".pdf");
        file.transferTo(tempFile);

        // Extract text from PDF
        String pdfText = PDFImporter.readPdfText(tempFile.getPath());
        
        // Convert text to events
        List<Event> events = Converter.convertScheduleToEvents(pdfText);
        
        // Associate events with user and save
        events.forEach(event -> {
            event.getUsers().add(user);
            saveEvent(event);
        });
        
        // Cleanup
        tempFile.delete();
        
        return events;
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
}