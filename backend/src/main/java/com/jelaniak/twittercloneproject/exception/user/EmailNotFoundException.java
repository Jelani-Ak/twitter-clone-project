package com.jelaniak.twittercloneproject.exception.user;

public class EmailNotFoundException extends Exception {
    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public EmailNotFoundException(Throwable cause) {
        super(cause);
    }

    public EmailNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
