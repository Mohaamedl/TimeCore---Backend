package com.odin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.odin.exception.DuplicateScheduleException;
import com.odin.model.Event;
import com.odin.model.Schedule;
import com.odin.model.User;
import com.odin.repository.ScheduleRepository;

/**
 * Implementation of the ScheduleService interface.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EventService eventService;

    /**
     * Default constructor.
     */
    public ScheduleServiceImpl() {
    }

    /**
     * Finds schedules associated with a user
     *
     * @param user User whose schedules to find
     * @return List of schedules belonging to the user
     * @throws Exception if retrieval fails
     */
    @Override
    public ArrayList<Schedule> findSchedulesByUser(User user) throws Exception {
        return (ArrayList<Schedule>) scheduleRepository.findByUserId(user.getId().toString());
    }

    /**
     * Finds schedules by user ID.
     *
     * @param userId The ID of the user.
     * @return An ArrayList of schedules.
     * @throws Exception if an error occurs.
     */
    @Override
    public ArrayList<Schedule> findSchedulesByUserId(String userId) throws Exception {
        return (ArrayList<Schedule>) scheduleRepository.findByUserId(userId);
    }

    /**
     * Finds schedules by group ID.
     *
     * @param userId The ID of the user.
     * @return An ArrayList of schedules.
     * @throws Exception if an error occurs.
     */
    @Override
    public ArrayList<Schedule> findSchedulesByGroup(Long userId) throws Exception {
        return (ArrayList<Schedule>) scheduleRepository.findByGroupId(userId.toString());
    }

    /**
     * Finds a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return The schedule.
     * @throws Exception if an error occurs.
     */
    @Override
    public Schedule findScheduleById(Long scheduleId) throws Exception {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new Exception("Schedule not found with id: " + scheduleId));
    }

    /**
     * Saves a schedule.
     *
     * @param schedule The schedule to save.
     * @return The saved schedule.
     */
    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to delete.
     */
    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    /**
     * Joins a list of schedules.
     *
     * @param scheduleList The list of schedules to join.
     * @return A pair containing the joined schedule and a list of events.
     */
    @Override
    public Pair<Schedule, List<Event>> joinSchedules(ArrayList<Schedule> scheduleList) {
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException("Schedule list cannot be null or empty");
        }

        Schedule joinedSchedule = new Schedule();
        List<Event> allEvents = new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            if (schedule.getEvents() != null) {
                allEvents.addAll(schedule.getEvents());
            }
        }

        joinedSchedule.setEvents(new HashSet<>(allEvents));
        joinedSchedule.setName("Merged Schedule");
        return Pair.of(joinedSchedule, allEvents);
    }

    /**
     * Finds conflicting events in a list of schedules.
     *
     * @param scheduleList The list of schedules to check.
     * @return A list of conflicting events.
     */
    @Override
    public List<Event> findConflictingEvents(ArrayList<Schedule> scheduleList) {
        return null;
    }

    /**
     * Updates a schedule.
     *
     * @param schedule The schedule to update.
     * @return The updated schedule.
     */
    @Override
    public Schedule updateschedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /**
     * Adds events to a schedule.
     *
     * @param schedule The schedule to add events to.
     * @param eventList The list of events to add.
     * @return The updated schedule.
     */
    @Override
    public Schedule addEventsSchedule(Schedule schedule, ArrayList<Event> eventList) {
        if (eventList == null || eventList.isEmpty()) {
            throw new IllegalArgumentException("Event list cannot be null or empty");
        }

        if (schedule.getEvents() == null) {
            schedule.setEvents(new HashSet<>());
        }

        schedule.getEvents().addAll(eventList);
        return scheduleRepository.save(schedule);
    }

    /**
     * Updates a schedule.
     *
     * @param schedule The schedule to update.
     * @return The updated schedule.
     */
    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /**
     * Imports a schedule from a file.
     *
     * @param file The file to import from.
     * @param name The name of the schedule.
     * @param user The user who owns the schedule.
     * @return The imported schedule.
     * @throws Exception if an error occurs.
     */
    @Override
    public Schedule importScheduleFromFile(MultipartFile file, String name, User user) throws Exception {
        // Check if schedule with same name exists for user
        List<Schedule> existingSchedules = scheduleRepository.findByUserIdAndName(
            user.getId().toString(), 
            name
        );
        
        if (!existingSchedules.isEmpty()) {
            throw new DuplicateScheduleException("Schedule with name " + name + " already exists");
        }

        // Import events from file
        List<Event> events = eventService.importEventsFromPdf(file, user);
        
        // Create new schedule
        Schedule schedule = new Schedule();
        schedule.setName(name);
        schedule.setUserId(user.getId().toString());
        schedule.setEvents(new HashSet<>(events));
        
        // Remove duplicate events
        Set<Event> uniqueEvents = new HashSet<>();
        for (Event event : events) {
            boolean isDuplicate = false;
            for (Event existing : uniqueEvents) {
                if (isDuplicateEvent(event, existing)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueEvents.add(event);
            }
        }
        
        schedule.setEvents(uniqueEvents);
        return scheduleRepository.save(schedule);
    }

    private boolean isDuplicateEvent(Event event1, Event event2) {
        return event1.getSummary().equals(event2.getSummary()) &&
               event1.getStartDateTime().equals(event2.getStartDateTime()) &&
               event1.getEndDateTime().equals(event2.getEndDateTime()) &&
               event1.getLocation().equals(event2.getLocation());
    }

    @Override
    public List<Event> findConflitingEvents(ArrayList<Schedule> scheduleList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
