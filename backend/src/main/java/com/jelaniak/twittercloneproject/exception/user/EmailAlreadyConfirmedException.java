package com.jelaniak.twittercloneproject.exception.user;

public class EmailAlreadyConfirmedException extends Exception {
    public EmailAlreadyConfirmedException() {
    }

    public EmailAlreadyConfirmedException(String errorMessage) {
        super(errorMessage);
    }

    public EmailAlreadyConfirmedException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyConfirmedException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
