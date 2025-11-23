package org.example.util;

/**
 * Unchecked exception thrown when user requests to exit/cancel an operation.
 * This exception is caught at the top level to gracefully return to the main menu.
 */
public class ExitException extends RuntimeException {

    public ExitException() {
        super("Operation cancelled by user");
    }

    public ExitException(String message) {
        super(message);
    }
}

