package com.jelaniak.twittercloneproject.exception;

public class BadCredentialsException extends Exception {
    public BadCredentialsException() {
    }

    public BadCredentialsException(String errorMessage) {
        super(errorMessage);
    }

    public BadCredentialsException(Throwable cause) {
        super(cause);
    }

    public BadCredentialsException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
