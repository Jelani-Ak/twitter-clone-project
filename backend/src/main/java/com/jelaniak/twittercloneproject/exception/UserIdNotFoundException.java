package com.jelaniak.twittercloneproject.exception;

public class UserIdNotFoundException extends Exception {
    public UserIdNotFoundException() {
    }

    public UserIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public UserIdNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserIdNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
