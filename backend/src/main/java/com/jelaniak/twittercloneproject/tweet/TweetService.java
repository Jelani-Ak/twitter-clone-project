package com.jelaniak.twittercloneproject.tweet;

import com.jelaniak.twittercloneproject.exception.TweetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

    public Tweet createTweet(Tweet tweet) {
        tweet.setTweetId(UUID.randomUUID().toString());
        tweetRepository.save(tweet);
        return tweet;
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet findTweetById(String id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet by id: [" + id + "] was not found."));
    }

    public Tweet updateTweet(String id, Tweet tweet) {

        tweet.setText(tweet.getText());
        tweet.setMediaUrl(tweet.getMediaUrl());
        tweet.setTweetType(tweet.getTweetType());

        tweetRepository.save(tweet);
        return tweet;
    }

    public void deleteTweet(String id) {
        tweetRepository.deleteById(id);
    }
}
