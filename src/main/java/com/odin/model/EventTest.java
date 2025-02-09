package com.odin.model;

import java.time.LocalDateTime;

/**
 * Test class for Event functionality.
 * Demonstrates creation and formatting of events.
 */
public class EventTest {

    /**
     * Default constructor
     */
    public EventTest() {
        // Default constructor
    }

    /**
     * Main method for testing Event class functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        Event event = new Event(
                "Meeting with Team",
                LocalDateTime.of(2025, 1, 25, 10, 0, 0),
                LocalDateTime.of(2025, 1, 25, 12, 0, 0),
                "Conference Room A",
                "Monthly team sync-up meeting",
                "12345"
        );


        System.out.println("Event details:");
        System.out.println(event.toString());


        System.out.println("\nEvent in ICS format:");
        System.out.println(event.toICSFormat());
    }
}
