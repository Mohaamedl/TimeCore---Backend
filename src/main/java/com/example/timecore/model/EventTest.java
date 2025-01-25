package com.example.timecore.model;

import java.time.LocalDateTime;

public class EventTest {

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
