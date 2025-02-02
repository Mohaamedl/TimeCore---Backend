package com.odin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odin.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUsersId(Long userId);
}