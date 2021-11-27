package com.jelaniak.twittercloneproject.tweet;

import com.jelaniak.twittercloneproject.exception.TweetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.UUID;
import java.util.Calendar;


@Service
@RequiredArgsConstructor
public class TweetService implements Serializable {

    private final TweetRepository tweetRepository;

    public void createTweet(Tweet tweet) {
        tweet.setTweetId(UUID.randomUUID().toString());
        tweet.setUsername(tweet.getUsername());
        tweet.setUrl(tweet.getUrl());
        tweet.setText(tweet.getText());
        tweet.setMediaUrl(tweet.getMediaUrl());
        tweet.setCommentCount(tweet.getCommentCount());
        tweet.setRetweetCount(tweet.getRetweetCount());
        tweet.setCreatedDate(Calendar.getInstance().toString());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());
        tweetRepository.save(tweet);
    }

    public void findAllTweets() {
        tweetRepository.findAll();
    }

    public void findTweetById(String id) {
        tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet by id: [" + id + "] was not found."));
    }

    public void deleteTweet(String id) {
        tweetRepository.deleteById(id);
    }
}
