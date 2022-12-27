package com.jelaniak.twittercloneproject.exception.user;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyExistsException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
