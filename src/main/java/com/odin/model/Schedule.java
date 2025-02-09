package com.odin.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a schedule entity.
 */
@Data
@Entity
@Table(name = "schedules") 
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true) 
    private String description;

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = true)
    private Date updated_at;

    @Column(nullable = true)
    private String color;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = true)
    private String groupId;

    @ManyToMany
    @JoinTable(
        name = "schedule_events",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new HashSet<>();

    /**
     * Default constructor.
     */
    public Schedule() {
    }

    /**
     * Method to be executed upon creation of the entity.
     */
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }

    /**
     * Method to be executed upon updating the entity.
     */
    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }
}
