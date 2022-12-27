package com.jelaniak.twittercloneproject.utils;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashSet;

public class TweetUtility {
    public static Tweet buildNewTweet(int number) {
        Tweet tweet = new Tweet();

        tweet.setTweetId(new ObjectId());
        tweet.setUserId(new ObjectId());
        tweet.setMedia(new Media());
        tweet.setContent("Content " + number);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setLikeCount(number);
        tweet.setTweetType(tweet.getTweetType());

        return tweet;
    }
}
