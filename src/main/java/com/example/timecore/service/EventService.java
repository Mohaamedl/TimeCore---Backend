package com.example.timecore.service;

import com.example.timecore.model.Event;
import java.util.List;

/**
 * Service class that provides business logic for managing events.
 */
public class EventService {

    /**
     * Adds an event to the list of events.
     *
     * @param events the list of events
     * @param event the event to be added
     */
    public void addEvent(List<Event> events, Event event) {
        events.add(event);
    }

    /**
     * Removes an event from the list of events.
     *
     * @param events the list of events
     * @param event the event to be removed
     */
    public void removeEvent(List<Event> events, Event event) {
        events.remove(event);
    }

    /**
     * Checks if there is a conflict between two events.
     * A conflict occurs if the start time of one event is before the end time of the other,
     * and the end time of the first event is after the start time of the second event.
     *
     * @param event1 the first event
     * @param event2 the second event
     * @return true if the events overlap, false otherwise
     */
    public boolean hasConflict(Event event1, Event event2) {
        return event1.getStartDateTime().isBefore(event2.getEndDateTime())
                && event1.getEndDateTime().isAfter(event2.getStartDateTime());
    }

    /**
     * Removes an event from the list of events (e.g., cancelling an event).
     *
     * @param events the list of events
     * @param event the event to be removed
     */
    public void subtractEvent(List<Event> events, Event event) {
        events.remove(event);
    }

    /**
     * Checks for conflicts between all events in the list.
     * Prints a message if conflicts are found.
     *
     * @param events the list of events to check for conflicts
     */
    public void checkConflicts(List<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (hasConflict(events.get(i), events.get(j))) {
                    System.out.println("Conflict detected between event "
                            + events.get(i).getSummary() + " and event "
                            + events.get(j).getSummary());
                }
            }
        }
    }
}
