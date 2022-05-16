package com.jelaniak.twittercloneproject.exception;

public class UserIdNotFoundException extends Exception {
    public UserIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
