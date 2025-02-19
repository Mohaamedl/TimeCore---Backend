package com.odin.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Schedule))
        {
            return false;
        }
        Schedule other = (Schedule) o;
        return (Objects.equals(other.id,this.id) && Objects.equals(other.color,this.color)
                && Objects.equals(other.description, this.description) && Objects.equals(other.events, this.events)
                && Objects.equals(other.groupId, this.groupId) && Objects.equals(other.created_at,this.created_at)
                && Objects.equals(other.name, this.name) &&  Objects.equals(other.updated_at, this.updated_at)
                && Objects.equals(other.userId, this.userId));
    }

    @Override
    public int hashCode()
    {
        int prime = 31;
        int result = prime;

        result += prime * result + (this.id == null ? 0 : this.id.hashCode());
        result += prime * result + (this.color == null ? 0 : this.color.hashCode());
        result += prime * result + (this.description == null ? 0 : this.description.hashCode());
        result += prime * result + (this.events == null ? 0 : this.events.hashCode());
        result += prime * result + (this.groupId == null ? 0 : this.groupId.hashCode());
        result += prime * result + (this.name == null ? 0 : this.name.hashCode());
        result += prime * result + (this.created_at == null ? 0 : this.created_at.hashCode());
        result += prime * result + (this.userId == null ? 0 : this.userId.hashCode());
        return result;
    }
}
