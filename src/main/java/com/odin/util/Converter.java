package com.odin.util;

import com.odin.model.Event;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for converting schedule text into Event objects.
 * Handles parsing of schedule information including UFCD codes, instructor details,
 * and time slots.
 */
public class Converter {
    
    private static final Map<String, String> ufcdInstructorMap = new HashMap<>();
    
    private static final Map<String, Integer> MONTH_MAP = new HashMap<>();

    private static final  Stack<String> WEEKDAYS =  new Stack<String>();


    static {
        WEEKDAYS.add("Segunda-feira");
        WEEKDAYS.add("Terça-feira");
        WEEKDAYS.add("Quarta-feira");
        WEEKDAYS.add("Quinta-feira");
        WEEKDAYS.add("Sexta-feira");
        WEEKDAYS.add("Sábado");
        WEEKDAYS.add("Domingo");
    }




    static {
        MONTH_MAP.put("Janeiro", 1);
        MONTH_MAP.put("Fevereiro", 2);
        MONTH_MAP.put("Março", 3);
        MONTH_MAP.put("Abril", 4);
        MONTH_MAP.put("Maio", 5);
        MONTH_MAP.put("Junho", 6);
        MONTH_MAP.put("Julho", 7);
        MONTH_MAP.put("Agosto", 8);
        MONTH_MAP.put("Setembro", 9);
        MONTH_MAP.put("Outubro", 10);
        MONTH_MAP.put("Novembro", 11);
        MONTH_MAP.put("Dezembro", 12);
    }

    /**
     * TODO 
     * 
     * Some dates are bieng parsed wrongly,
     * Rerwite  this method
     * 
     */
    
    /**
     * Converts schedule text into a list of Event objects.
     *
     * @param scheduleText The raw schedule text to be parsed
     * @return List of Event objects created from the schedule
     */
    public static List<Event> convertScheduleToEvents(String scheduleText) {
        List<Event> events = new ArrayList<>();
        extractUFCDInstructorMapping(scheduleText);
        
        String[] lines = scheduleText.split("\n");
        int currentMonth = 0;
        int currentYear = 0;
        List<Integer> currentWeekDays = new ArrayList<>();
        
        for (String line : lines) {
            line = line.trim();
            
            // Extract month and year using pattern: MONTH YEAR (e.g., "Janeiro 2025")
            for (Map.Entry<String, Integer> month : MONTH_MAP.entrySet()) {
                Pattern pattern = Pattern.compile(month.getKey() + "\\s+(\\d{4})");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    currentMonth = month.getValue();
                    currentYear = Integer.parseInt(matcher.group(1));
                    break;
                }
            }
            
            // Process line containing day numbers
            if (line.matches("^\\s*\\d+(?:\\s+\\d+)*\\s*$")) {
                currentWeekDays.clear();
                for (String part : line.trim().split("\\s+")) {
                    try {
                        int day = Integer.parseInt(part);
                        if (day >= 1 && day <= 31) {
                            currentWeekDays.add(day);
                        }
                    } catch (NumberFormatException e) {
                        // Ignore non-numeric parts
                    }
                }
                continue;
            }
            
            // Process lines with time slots and UFCDs
            if (line.matches("\\d{2}:\\d{2}.*")) {
                String[] slots = line.trim().split("\\s+");
                int slotIndex = 0;
                
                for (int i = 0; i < slots.length; i += 2) {
                    if (slots[i].matches("\\d{2}:\\d{2}") && i + 1 < slots.length) {
                        LocalTime time = LocalTime.parse(slots[i]);
                        String ufcdCode = slots[i + 1];
                        
                        if (slotIndex < currentWeekDays.size()) {
                            int day = currentWeekDays.get(slotIndex);
                            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
                            
                            Event event = createEvent(date, time, ufcdCode);
                            if (event != null) {
                                mergeOrAddEvent(events, event);
                            }
                            slotIndex++;
                        }
                    }
                }
            }
        }
        
        return events;
    }
    
    /**
     * Extracts UFCD and instructor information from the schedule text.
     * Pattern matches: UFCD_CODE UFCD_NAME (UFCD_CODE) INSTRUCTOR_NAME (forINSTRUCTOR_CODE)
     *
     *
     * @param text The raw schedule text containing UFCD and instructor mappings
     */
    private static void extractUFCDInstructorMapping(String text) {
        String[] lines = text.split("\n");
        boolean mappingSection = false;
        
        for (String line : lines) {
            line = line.trim();
            
            if (line.startsWith("Abrv Nome Formador")) {
                mappingSection = true;
                continue;
            }
            
            if (mappingSection && line.startsWith("Totais:")) {
                break;
            }
            
            if (mappingSection && line.matches("^\\d{4}\\s+.*")) {
                try {
                    // Pattern matches: UFCD_CODE UFCD_NAME (UFCD_CODE) FORMADOR_NAME (forFORMADOR_CODE)
                    // Example: 5412 Programação de computadores (5412) João Silva (for1234)
                    Pattern pattern = Pattern.compile("(\\d{4})\\s+([^(]+)\\((\\d{4})\\)\\s+([^(]+)\\(for(\\d{4})\\).*");
                    Matcher matcher = pattern.matcher(line);
                    
                    if (matcher.find()) {
                        String ufcdCode = matcher.group(1);
                        String ufcdName = matcher.group(2).trim();
                        String instructorName = matcher.group(4).trim();
                        String instructorCode = "for" + matcher.group(5);

                        ufcdInstructorMap.put(ufcdCode, ufcdName + " -- " + instructorName + " -- " + instructorCode);
                        
                        //System.out.println("Mapped: " + ufcdCode + " -> " + ufcdName + " -> " + instructorName + " - " + instructorCode);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        }
        
        // Debug: print final mapping
        //System.out.println("\nFinal UFCD mapping:");
        //ufcdInstructorMap.forEach((code, info) -> System.out.println(code + " -> " + info));
    }
    
    /**
     * Creates an Event object from the given date, time, and UFCD code.
     *
     * @param date The date of the event
     * @param startTime The start time of the event
     * @param ufcdCode The UFCD code for the event
     * @return Event object if successful, null if UFCD code not found in mapping
     */
    private static Event createEvent(LocalDate date, LocalTime startTime, String ufcdCode) {
        if (!ufcdInstructorMap.containsKey(ufcdCode)) {
            System.err.println("UFCD not found in mapping: " + ufcdCode);
            return null;
        }
        
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        
        String[] parts = ufcdInstructorMap.get(ufcdCode).split(" -- ");
        String ufcdName = parts[0];
        String instructorName = parts[1];
        String instructorCode = parts[2];
        
        return new Event(
            "UFCD " + ufcdCode + " - " + ufcdName,
            startDateTime,
            endDateTime,
            "Centro de Emprego e Formação Profissional do Porto",
            "Formador(a): " + instructorName + " - Numero: " + instructorCode,
            UUID.randomUUID().toString()
        );
    }
    
    /**
     * Merges or adds an event to the event list.
     * Events are merged if they have the same summary, description,
     * and are consecutive in time.
     *
     * @param events The list of existing events
     * @param newEvent The new event to be added or merged
     */
    private static void mergeOrAddEvent(List<Event> events, Event newEvent) {
        Optional<Event> existingEvent = events.stream()
            .filter(e -> e.getSummary().equals(newEvent.getSummary()) &&
                        e.getDescription().equals(newEvent.getDescription()) &&
                        (e.getEndDateTime().equals(newEvent.getStartDateTime()) ||
                         e.getStartDateTime().equals(newEvent.getEndDateTime())))
            .findFirst();
        
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            LocalDateTime newStart = event.getStartDateTime().isBefore(newEvent.getStartDateTime()) ?
                event.getStartDateTime() : newEvent.getStartDateTime();
            LocalDateTime newEnd = event.getEndDateTime().isAfter(newEvent.getEndDateTime()) ?
                event.getEndDateTime() : newEvent.getEndDateTime();
            
            event.setStartDateTime(newStart);
            event.setEndDateTime(newEnd);
        } else {
            events.add(newEvent);
        }
    }
    
    private static boolean canBeMerged(Event event1, Event event2) {
        return event1.getSummary().equals(event2.getSummary()) &&
               event1.getDescription().equals(event2.getDescription()) &&
               event1.getEndDateTime().equals(event2.getStartDateTime());
    }

    private static boolean isDuplicate(Event event1, Event event2) {
        return event1.getSummary().equals(event2.getSummary()) &&
               event1.getDescription().equals(event2.getDescription()) &&
               event1.getStartDateTime().equals(event2.getStartDateTime()) &&
               event1.getEndDateTime().equals(event2.getEndDateTime());
    }

    /**
     * Example of use
     */
    public static void main(String[] args) {
        try {
            System.out.println("Reading PDF...");
            String pdfText = PDFImporter.readPdfFromResources("cronograma_Dezembro_24.0226.pdf");
            
            System.out.println("Text in PDF:");
            System.out.println("----------------------------------------");
            System.out.println(pdfText);
            System.out.println("----------------------------------------");
            
            System.out.println("Coverting to Events...");
            List<Event> events = convertScheduleToEvents(pdfText);
            
            System.out.println("Total events converted " + events.size());
            if (events.isEmpty()) {
                System.out.println("No events were converted.");
                return;
            }
            
            events.sort((e1, e2) -> e1.getStartDateTime().compareTo(e2.getStartDateTime()));
            
            for (Event event : events) {
                System.out.println(event.toICSFormat());
            }
            
        } catch (IOException e) {
            System.err.println("Error processing PDF file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
