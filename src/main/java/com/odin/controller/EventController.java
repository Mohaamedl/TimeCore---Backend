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
import com.odin.service.EventService;
import com.odin.service.UserService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired 
    private UserService userService;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importEventsFromPDF(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String jwt) {
        try {
            // Verify user
            User user = userService.findUserByJwt(jwt);
            
            // Process PDF and save events
            List<Event> events = eventService.importEventsFromPdf(file);
            
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error processing PDF: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestHeader("Authorization") String jwt) {
        try {
            // Verify user
            userService.findUserByJwt(jwt);
            
            return ResponseEntity.ok(eventService.getAllEvents());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) {
        try {
            // Verify user
            userService.findUserByJwt(jwt);
            
            eventService.deleteEvent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error deleting event: " + e.getMessage());
        }
    }
}