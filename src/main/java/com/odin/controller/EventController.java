package com.odin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.odin.model.Event;
import com.odin.model.User;
import com.odin.service.interfaces.EventService;
import com.odin.service.interfaces.UserService;

/**
 * REST Controller for managing Event operations.
 * Handles importing, retrieving, updating and deleting events.
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    /**
     * Default constructor
     */
    public EventController() {
        // Default constructor
    }

    @Autowired
    private EventService eventService;

    @Autowired 
    private UserService userService;

    /**
     * Imports events from a PDF file
     * @param file The PDF file containing event data
     * @param jwt JWT authentication token
     * @return ResponseEntity containing imported events or error message
     */
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importEventsFromPDF(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            
            List<Event> events = eventService.importEventsFromPdf(file, user);
            
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error processing PDF: " + e.getMessage());
        }
    }

    /**
     * Retrieves all events in the system
     * @param jwt JWT authentication token
     * @return ResponseEntity containing list of all events
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt);
            
            return ResponseEntity.ok(eventService.getAllEvents());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all events for a specific user
     * @param userId ID of the user to get events for
     * @param jwt JWT authentication token
     * @return ResponseEntity containing list of user's events
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getEventsByUser(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt);
            List<Event> events = eventService.getEventsByUser(userId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Adds a user to an existing event
     * @param eventId ID of the event
     * @param userId ID of the user to add
     * @param jwt JWT authentication token
     * @return ResponseEntity containing updated event or error message
     */
    @PostMapping("/{eventId}/users/{userId}")
    public ResponseEntity<?> addUserToEvent(
            @PathVariable Long eventId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt);
            Event event = eventService.addUserToEvent(eventId, userId);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletes an event by ID
     * @param id ID of event to delete
     * @param jwt JWT authentication token
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) {
        try {

            userService.findUserByJwt(jwt);
            
            eventService.deleteEvent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error deleting event: " + e.getMessage());
        }
    }
}