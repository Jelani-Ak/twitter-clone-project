package com.jelaniak.twittercloneproject.exception;

public class BadCredentialsException extends Exception {
    public BadCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
