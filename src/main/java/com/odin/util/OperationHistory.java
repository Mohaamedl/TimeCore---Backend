package com.odin.util;

import java.time.LocalDateTime;
import java.util.Stack;

public class OperationHistory {
    private static final Stack<String> operationStack = new Stack<>();
    
    public static void pushOperation(String operation) {
        operationStack.push(operation + " at " + LocalDateTime.now());
    }
    
    public static String getLastOperation() {
        return operationStack.isEmpty() ? null : operationStack.peek();
    }
    
    public static void undoLastOperation() {
        if (!operationStack.isEmpty()) {
            operationStack.pop();
        }
    }
}