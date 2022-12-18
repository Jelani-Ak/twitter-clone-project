package com.jelaniak.twittercloneproject.exception.comment;

public class ConfirmationTokenNotFoundException extends Exception {
    public ConfirmationTokenNotFoundException() {
    }

    public ConfirmationTokenNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ConfirmationTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConfirmationTokenNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
