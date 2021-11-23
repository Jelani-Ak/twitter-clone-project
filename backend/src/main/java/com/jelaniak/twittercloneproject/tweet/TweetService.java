package com.jelaniak.twittercloneproject.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

    public Tweet addTweet(Tweet tweet) {
        tweet.setTweetId(UUID.randomUUID().toString());
        return tweet;
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Optional<Tweet> findTweetById(String id) {
        return tweetRepository.findById(id);
    }

    public Tweet updateTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public void deleteTweet(String id) {
        tweetRepository.deleteById(id);
    }
}
