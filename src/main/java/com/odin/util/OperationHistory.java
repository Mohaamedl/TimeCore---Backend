package com.odin.util;

import java.time.LocalDateTime;
import java.util.Stack;

/**
 * Utility class for tracking operation history.
 * Implements a stack-based approach to track and manage operations.
 */
public class OperationHistory {
    
    /** Stack to store operation history */
    private static final Stack<String> operationStack = new Stack<>();
    
    /**
     * Default constructor.
     */
    public OperationHistory() {
    }

    /**
     * Adds a new operation to the history
     * 
     * @param operation The operation to record
     */
    public static void pushOperation(String operation) {
        operationStack.push(operation + " at " + LocalDateTime.now());
    }
    
    /**
     * Retrieves the last operation performed.
     *
     * @return The last operation as a string.
     */
    public static String getLastOperation() {
        return operationStack.isEmpty() ? null : operationStack.peek();
    }
    
    /**
     * Undoes the last operation by removing it from the history.
     */
    public static void undoLastOperation() {
        if (!operationStack.isEmpty()) {
            operationStack.pop();
        }
    }
}