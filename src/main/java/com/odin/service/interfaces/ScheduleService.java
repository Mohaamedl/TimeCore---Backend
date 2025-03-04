package com.odin.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;

import com.odin.model.Event;
import com.odin.model.Schedule;
import com.odin.model.User;

/**
 * Service interface for Schedule management operations.
 * Handles business logic for schedules including CRUD operations,
 * conflict detection and schedule merging.
 */
public interface ScheduleService {
    
    /**
     * Finds schedules associated with a user
     * 
     * @param user User whose schedules to find
     * @return List of schedules belonging to the user
     * @throws Exception if retrieval fails
     */
    ArrayList<Schedule> findSchedulesByUser(User user) throws Exception;

    /**
     * Finds schedules by user ID.
     *
     * @param userId The ID of the user.
     * @return An ArrayList of schedules.
     * @throws Exception if an error occurs.
     */
    public ArrayList<Schedule> findSchedulesByUserId(String userId) throws Exception;

    /**
     * Finds schedules by group ID.
     *
     * @param userId The ID of the user.
     * @return An ArrayList of schedules.
     * @throws Exception if an error occurs.
     */
    public ArrayList<Schedule> findSchedulesByGroup(Long userId) throws Exception;

    /**
     * Finds a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return The schedule.
     * @throws Exception if an error occurs.
     */
    public Schedule findScheduleById(Long scheduleId) throws Exception;

    /**
     * Saves a schedule.
     *
     * @param schedule The schedule to save.
     * @return The saved schedule.
     */
    public Schedule saveSchedule(Schedule schedule);

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to delete.
     */
    public void deleteSchedule(Long id);

    /**
     * Joins a list of schedules.
     *
     * @param scheduleList The list of schedules to join.
     * @return A pair containing the joined schedule and a list of events.
     */
    public Pair<Schedule,List<Event>> joinSchedules(ArrayList<Schedule> scheduleList);

    /**
     * Finds conflicting events in a list of schedules.
     *
     * @param scheduleList The list of schedules to check.
     * @return A list of conflicting events.
     */
    public List<Event> findConflitingEvents(ArrayList<Schedule> scheduleList);

    /**
     * Updates a schedule.
     *
     * @param schedule The schedule to update.
     * @return The updated schedule.
     */
    public Schedule updateschedule(Schedule schedule);

    /**
     * Adds events to a schedule.
     *
     * @param schedule The schedule to add events to.
     * @param eventList The list of events to add.
     * @return The updated schedule.
     */
    public Schedule addEventsSchedule(Schedule schedule, ArrayList<Event> eventList);

    /**
     * Updates a schedule.
     *
     * @param schedule The schedule to update.
     * @return The updated schedule.
     */
    public Schedule updateSchedule(Schedule schedule);

    /**
     * Finds conflicting events in a list of schedules.
     *
     * @param scheduleList The list of schedules to check.
     * @return A list of conflicting events.
     */
    public List<Event> findConflictingEvents(ArrayList<Schedule> scheduleList);

    /**
     * Imports a schedule from a file.
     *
     * @param file The file to import from.
     * @param name The name of the schedule.
     * @param user The user who owns the schedule.
     * @return The imported schedule.
     * @throws Exception if an error occurs.
     */
    public Schedule importScheduleFromFile(MultipartFile file, String name, User user) throws Exception;
}

