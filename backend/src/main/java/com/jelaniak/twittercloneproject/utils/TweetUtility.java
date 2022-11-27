package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;

public class TweetUtility {
    public static Tweet getNewTweet(int number, User user, Media media) {
        Tweet tweet = new Tweet();

        tweet.setTweetId(new ObjectId());
        tweet.setUser(user);
        tweet.setMedia(media);
        tweet.setContent("Content " + number);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());

        return tweet;
    }
}
