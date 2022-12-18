package com.jelaniak.twittercloneproject.exception.comment;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException() {
    }

    public CommentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }

    public CommentNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
