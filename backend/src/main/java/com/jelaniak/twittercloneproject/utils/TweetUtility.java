package com.jelaniak.twittercloneproject.utils;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TweetUtility {
    public static Tweet getNewTweet(int number, User user, Media media) {
        Tweet tweet = new Tweet();

        tweet.setTweetId(new ObjectId());
        tweet.setTweetUrl("http://www.tweet" + number + ".co.uk/example");
        tweet.setUser(user);
        tweet.setMedia(media);
        tweet.setContent("Content " + number);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComment(new ArrayList<>());
        tweet.setCommentCount(tweet.getComment().size());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());

        return tweet;
    }
}
