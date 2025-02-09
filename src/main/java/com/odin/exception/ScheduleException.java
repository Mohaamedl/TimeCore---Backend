package com.odin.exception;

/**
 * Custom exception class for Schedule-related errors.
 * Used to handle specific schedule operation failures.
 *
 * @author TimeCore Team
 * @version 1.0
 */
public class ScheduleException extends RuntimeException {
    /**
     * Constructs a new ScheduleException with specified message
     *
     * @param message The error message
     */
    public ScheduleException(String message) {
        super(message);
    }
}