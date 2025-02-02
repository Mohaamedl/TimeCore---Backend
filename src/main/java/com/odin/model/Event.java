package com.odin.Model;

import java.time.LocalDateTime;

public class Event {

    private String summary;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String description;
    private String uid;

    public Event(String summary, LocalDateTime startDateTime, LocalDateTime endDateTime, String location, String description, String uid) {
        this.summary = summary;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.description = description;
        this.uid = uid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Converts the event to an ICS format string.
     *
     * @return the event in ICS format (String)
     */
    public String toICSFormat() {
        return "BEGIN:VEVENT\n" +
                "SUMMARY:" + summary + "\n" +
                "DTSTART:" + startDateTime.toString().replace("T", "T") + "\n" +
                "DTEND:" + endDateTime.toString().replace("T", "T") + "\n" +
                "LOCATION:" + location + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "UID:" + uid + "\n" +
                "END:VEVENT\n";
    }

    @Override
    public String toString() {
        return "Event{" +
                "summary='" + summary + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
