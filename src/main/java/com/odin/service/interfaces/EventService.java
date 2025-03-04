package com.odin.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.odin.model.Event;
import com.odin.model.User;

/**
 * Service interface for managing event operations.
 * Handles event creation, updates, retrievals and imports.
 */
public interface EventService {
    /**
     * Imports events from uploaded PDF file
     * @param file PDF file containing event data
     * @param user User who owns the events
     * @return List of imported events
     * @throws Exception if import fails
     */
    List<Event> importEventsFromPdf(MultipartFile file, User user) throws Exception;

    /**
     * Gets all events in the system
     * @return List of all events
     */
    List<Event> getAllEvents();

    /**
     * Saves or updates an event
     * @param event Event to save
     * @return Saved event
     */
    Event saveEvent(Event event);

    /**
     * Deletes an event
     * @param id ID of event to delete
     */
    void deleteEvent(Long id);

    /**
     * Gets events for a specific user
     * @param userId ID of user
     * @return List of user's events
     */
    List<Event> getEventsByUser(Long userId);

    /**
     * Adds a user to an event
     * @param eventId ID of event
     * @param userId ID of user to add
     * @return Updated event
     * @throws Exception if event or user not found
     */
    Event addUserToEvent(Long eventId, Long userId) throws Exception;

    /**
     * Imports events from CSV file
     * @param path Path to CSV file
     * @return List of imported events
     */  
    List<Event> importEventsFromCSV(String path);
}