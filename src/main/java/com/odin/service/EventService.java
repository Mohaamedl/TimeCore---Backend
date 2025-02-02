package com.odin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.odin.model.Event;

public interface EventService {
    List<Event> importEventsFromPdf(MultipartFile file) throws Exception;
    List<Event> getAllEvents();
    Event saveEvent(Event event);
    void deleteEvent(Long id);
     public List<Event> getEventsByUser(Long userId);
     public Event addUserToEvent(Long eventId, Long userId) throws Exception;

}