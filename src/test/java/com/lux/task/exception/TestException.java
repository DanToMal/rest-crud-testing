package com.lux.task.exception;

/**
 * Indicates an unexpected state during test execution.
 */
public class TestException extends RuntimeException {
    public TestException(String message) {
        super(message);
    }
}
