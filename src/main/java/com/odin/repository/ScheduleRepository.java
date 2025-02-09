package com.odin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odin.model.Schedule;

/**
 * Repository interface for Schedule entity.
 * Provides database operations for schedule management.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * Finds schedules associated with a specific user
     * 
     * @param id The ID of the schedule to find
     * @return Optional schedule
     */
    Optional<Schedule> findById(Long id);

    /**
     * Finds schedules by user ID.
     *
     * @param userId The ID of the user to find schedules for.
     * @return List of schedules belonging to the user.
     */
    List<Schedule> findByUserId(String userId);

    /**
     * Finds schedules by group ID.
     *
     * @param groupId The ID of the group to find schedules for.
     * @return List of schedules belonging to the group.
     */
    List<Schedule> findByGroupId(String groupId);

    /**
     * Finds schedules by user ID and name.
     *
     * @param userId The ID of the user to find schedules for.
     * @param name The name of the schedule.
     * @return List of schedules matching the user ID and name.
     */
    List<Schedule> findByUserIdAndName(String userId, String name);
}
