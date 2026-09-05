package org.lms.exception;

public class MissingArgumentException extends RuntimeException{

    public MissingArgumentException() {
        super();
    }

    public MissingArgumentException(String message) {
        super(message);
    }
}
