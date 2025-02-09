package com.odin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odin.model.Event;

/**
 * Repository interface for Event entity operations.
 * Provides database access methods for events.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    
    /**
     * Finds all events associated with a user
     * @param userId ID of the user
     * @return List of events belonging to the user
     */
    List<Event> findByUsersId(Long userId);
}