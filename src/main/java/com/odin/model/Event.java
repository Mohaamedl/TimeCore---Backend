package com.odin.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a scheduled event.
 * Manages event details including time, location and associated users.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class Event  implements Comparable<Event>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(unique = true)
    private String uid;

    @ManyToMany
    @JoinTable(
        name = "user_events",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Creates a new event with the specified details.
     *
     * @param summary Event title/summary
     * @param startDateTime Start date and time
     * @param endDateTime End date and time
     * @param location Event location
     * @param description Event description
     * @param uid Unique identifier for the event
     */
    public Event(String summary, LocalDateTime startDateTime, LocalDateTime endDateTime, 
                String location, String description, String uid) {
        this.summary = summary;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.description = description;
        this.uid = uid;
    }

    /**
     * Converts the event to iCalendar format.
     *
     * @return String containing the event in ICS format
     */
    public String toICSFormat() {
        return "BEGIN:VEVENT\n" +
                "SUMMARY:" + summary + "\n" +
                "DTSTART:" + startDateTime.toString() + "\n" +
                "DTEND:" + endDateTime.toString() + "\n" +
                "LOCATION:" + location + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "UID:" + uid + "\n" +
                "END:VEVENT\n";
    }

    /**
     * Gets the list of users associated with this event
     * @return Set of users participating in the event
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the users associated with this event
     * @param users Set of users to associate with the event
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Sets the event creation timestamp
     * @param createdAt Creation date and time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets the event description
     * @param description Description text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the event end date and time
     * @param endDateTime End date and time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Sets the event location
     * @param location Event location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the event start date and time
     * @param startDateTime Start date and time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Sets the event summary/title
     * @param summary Event summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Sets the event update timestamp
     * @param updatedAt Update date and time
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    /**
     * Gets the event creation timestamp
     * @return Creation date and time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the event description
     * @return Description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the event end date and time
     * @return End date and time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Gets the event ID
     * @return Event ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the event location
     * @return Event location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the event start date and time
     * @return Start date and time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Gets the event unique identifier
     * @return Unique identifier
     */
    public String getUid() {
        return uid;
    }

    /**
     * Gets the event summary
     * @return Event summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Gets the event update timestamp
     * @return Update date and time
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!(obj instanceof  Event))
            return false;
        Event other = (Event) obj;
        return other.id.equals(this.id) && other.createdAt.equals(this.createdAt)
                && other.description.equals(this.description) && other.endDateTime.equals(this.endDateTime)
                && other.location.equals(this.location) && other.summary.equals(this.summary) && other.uid.equals(this.uid);
    }
    /**
     * returns the hashcode
     * @return int
     * */
    @Override
    public int hashCode()
    {
        int PRIME = 31;
        int result =  PRIME;
        result = PRIME * result + (this.id == null ?  0 :  PRIME * this.id.hashCode());
        result = PRIME * result + (this.createdAt == null ?  0 :  PRIME * this.createdAt.hashCode());
        result = PRIME * result + (this.description == null ? 0 :  PRIME * this.description.hashCode());
        result = PRIME * result + (this.endDateTime == null ? 0 :  PRIME * this.endDateTime.hashCode());
        result = PRIME * result + (this.location == null ? 0 :  PRIME * this.location.hashCode());
        result = PRIME * result + (this.summary == null ? 0 :  PRIME * this.summary.hashCode());
        result = PRIME * result + (this.uid == null ?  0 :  PRIME * this.uid.hashCode());
        result = PRIME * result + (this.users == null ? 0 :  PRIME * this.users.hashCode());
        result = PRIME * result + (this.startDateTime == null ? 0 :  PRIME * this.startDateTime.hashCode());
        result = PRIME * result + (this.updatedAt == null ? 0 :  PRIME * this.updatedAt.hashCode());
        return result;
    }

    /**
     * @param o the object to be compared. 
     * @return int
     */
    @Override
    public int compareTo(Event o) {
        int result;
        if (this.equals(o)) {
            return 0;
        }
        if (this.startDateTime.isBefore(o.startDateTime)) {
            result = -1;
        }
        else {
            result = 1;
        }
        return result;
    }


    public static void main(String[] args) {
        Set<Event> listEvents =  new HashSet<Event>();


    }
}