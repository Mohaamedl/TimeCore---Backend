/*
package com.odin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private Date criated_at;

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
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )






    public String getColor() {
        return color;
    }

    public Date getCriated_at() {
        return criated_at;
    }

    public String getDescription() {
        return description;
    }

    public String getGroupId() {
        return groupId;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
*/
