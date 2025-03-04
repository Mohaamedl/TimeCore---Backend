package com.odin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.odin.exception.DuplicateScheduleException;
import com.odin.model.Event;
import com.odin.model.Schedule;
import com.odin.model.User;
import com.odin.service.interfaces.ScheduleService;
import com.odin.service.interfaces.UserService;

/**
 * Controller responsible for managing schedules in the TimeCore application.
 * Provides endpoints for CRUD operations, schedule importing and conflict detection.
 * 
 * @author TimeCore Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    /**
     * Service for schedule operations
     */
    @Autowired
    private ScheduleService scheduleService;

    /**
     * Service for user operations
     */
    @Autowired
    private UserService userService;

    /**
     * Default constructor.
     */
    public ScheduleController() {
    }

    /**
     * Gets all schedules for the authenticated user
     * 
     * @param jwt The JWT authentication token
     * @return ResponseEntity containing list of schedules or error message
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUserSchedules(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            ArrayList<Schedule> schedules = scheduleService.findSchedulesByUser(user);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user schedules: " + e.getMessage());
        }
    }

    /**
     * Gets schedules for a specific group
     *
     * @param groupId ID of the group to fetch schedules for
     * @param jwt JWT token in the Authorization header
     * @return ResponseEntity containing list of schedules or error message
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroupSchedules(
            @PathVariable Long groupId,
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt); 
            ArrayList<Schedule> schedules = scheduleService.findSchedulesByGroup(groupId);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching group schedules: " + e.getMessage());
        }
    }

    /**
     * Creates a new schedule.
     *
     * @param schedule The schedule to create.
     * @param jwt      The JWT token for authentication.
     * @return ResponseEntity containing the created schedule or an error message.
     */
    @PostMapping
    public ResponseEntity<?> createSchedule(
            @RequestBody Schedule schedule,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            schedule.setUserId(user.getId().toString());
            Schedule savedSchedule = scheduleService.saveSchedule(schedule);
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating schedule: " + e.getMessage());
        }
    }

    /**
     * Updates an existing schedule.
     *
     * @param id The ID of the schedule to update.
     * @param schedule The schedule to update.
     * @param jwt      The JWT token for authentication.
     * @return ResponseEntity containing the updated schedule or an error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(
            @PathVariable Long id,
            @RequestBody Schedule schedule,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            Schedule existing = scheduleService.findScheduleById(id);
            
            if (!existing.getUserId().equals(user.getId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Not authorized to update this schedule");
            }
            
            schedule.setId(id);
            Schedule updated = scheduleService.updateSchedule(schedule);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating schedule: " + e.getMessage());
        }
    }

    /**
     * Deletes a schedule by ID.
     *
     * @param id  The ID of the schedule to delete.
     * @param jwt The JWT token for authentication.
     * @return ResponseEntity containing a success message or an error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            Schedule schedule = scheduleService.findScheduleById(id);
            
            if (!schedule.getUserId().equals(user.getId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Not authorized to delete this schedule");
            }
            
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok("Schedule deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting schedule: " + e.getMessage());
        }
    }

    /**
     * Joins multiple schedules into one
     *
     * @param scheduleIds List of schedule IDs to join
     * @param jwt JWT token in the Authorization header
     * @return ResponseEntity containing merged schedule and events list
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinSchedules(
            @RequestBody ArrayList<Long> scheduleIds,
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt); 
            ArrayList<Schedule> schedules = new ArrayList<>();
            
            for (Long id : scheduleIds) {
                schedules.add(scheduleService.findScheduleById(id));
            }
            
            var result = scheduleService.joinSchedules(schedules);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error joining schedules: " + e.getMessage());
        }
    }

    /**
     * Finds conflicting events between schedules
     *
     * @param scheduleIds List of schedule IDs to check for conflicts
     * @param jwt JWT token in the Authorization header
     * @return ResponseEntity containing list of conflicting events
     */
    @PostMapping("/conflicts")
    public ResponseEntity<?> findConflicts(
            @RequestBody ArrayList<Long> scheduleIds,
            @RequestHeader("Authorization") String jwt) {
        try {
            userService.findUserByJwt(jwt); 
            ArrayList<Schedule> schedules = new ArrayList<>();
            
            for (Long id : scheduleIds) {
                schedules.add(scheduleService.findScheduleById(id));
            }
            
            List<Event> conflicts = scheduleService.findConflictingEvents(schedules);
            return ResponseEntity.ok(conflicts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error finding conflicts: " + e.getMessage());
        }
    }

    /**
     * Adds events to an existing schedule
     *
     * @param id ID of the schedule to add events to
     * @param events List of events to add
     * @param jwt JWT token in the Authorization header
     * @return ResponseEntity containing updated schedule or error message
     */
    @PostMapping("/{id}/events")
    public ResponseEntity<?> addEventsToSchedule(
            @PathVariable Long id,
            @RequestBody ArrayList<Event> events,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            Schedule schedule = scheduleService.findScheduleById(id);
            
            if (!schedule.getUserId().equals(user.getId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Not authorized to modify this schedule");
            }
            
            Schedule updated = scheduleService.addEventsSchedule(schedule, events);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding events to schedule: " + e.getMessage());
        }
    }

    /**
     * Imports a schedule from a file
     *
     * @param file File containing schedule data
     * @param scheduleName Name for the new schedule
     * @param jwt JWT token in the Authorization header
     * @return ResponseEntity containing imported schedule or error message
     */
    @PostMapping("/import")
    public ResponseEntity<?> importSchedule(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String scheduleName,
            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            Schedule schedule = scheduleService.importScheduleFromFile(file, scheduleName, user);
            return ResponseEntity.ok(schedule);
        } catch (DuplicateScheduleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Schedule already exists: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error importing schedule: " + e.getMessage());
        }
    }
}
