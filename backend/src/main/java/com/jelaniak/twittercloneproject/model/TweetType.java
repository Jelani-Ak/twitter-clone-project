package com.jelaniak.twittercloneproject.model;

public enum TweetType {
    TWEET("Tweet"),
    RETWEET("Retweet"),
    MEDIA("Media"),
    LIKED("Label"),
    COMMENT("Comment");

    public final String label;

    TweetType(String label) {
        this.label = label;
    }
}
