package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;

public class TweetUtility {
    public static Tweet getNewTweet(int number, ObjectId userId, Media media) {
        Tweet tweet = new Tweet();

        tweet.setTweetId(new ObjectId());
        tweet.setUserId(userId);
        tweet.setMedia(media);
        tweet.setContent("Content " + number);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());

        return tweet;
    }
}
