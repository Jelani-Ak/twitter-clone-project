package com.jelaniak.twittercloneproject.exception.tweet;

public class TweetNotFoundException extends Exception {
    public TweetNotFoundException() {
    }

    public TweetNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public TweetNotFoundException(Throwable cause) {
        super(cause);
    }

    public TweetNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
