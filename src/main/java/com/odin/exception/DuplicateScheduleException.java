package com.odin.exception;

/**
 * Exception thrown when attempting to create a duplicate schedule.
 * Used to prevent multiple schedules with the same name for a user.
 */
public class DuplicateScheduleException extends RuntimeException {
    
    /**
     * Constructs exception with error message
     * @param message Description of the error
     */
    public DuplicateScheduleException(String message) {
        super(message);
    }
}