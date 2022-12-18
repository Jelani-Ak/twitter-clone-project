package com.jelaniak.twittercloneproject.exception.user;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistsException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
