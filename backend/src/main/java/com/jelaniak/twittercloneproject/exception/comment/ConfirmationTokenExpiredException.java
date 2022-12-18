package com.jelaniak.twittercloneproject.exception.comment;

public class ConfirmationTokenExpiredException extends Exception {
    public ConfirmationTokenExpiredException() {
    }

    public ConfirmationTokenExpiredException(String errorMessage) {
        super(errorMessage);
    }

    public ConfirmationTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public ConfirmationTokenExpiredException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
