package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetService implements Serializable {

    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;

    public Tweet createTweet(Tweet tweet) {
        tweet.setUser(tweet.getUser());
        tweet.setTweetUrl(tweet.getTweetUrl());
        tweet.setContent(tweet.getContent());
        tweet.setMedia(tweet.getMedia());
        tweet.setCommentCount(tweet.getCommentCount());
        tweet.setRetweetCount(tweet.getRetweetCount());
        tweet.setCreatedDate(tweet.getCreatedDate());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());
        return tweetRepository.save(tweet);
    }

    public Comment createTweetComment(String tweetId, Comment comment) throws Exception {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);

        if (tweet.isEmpty()) {
            throw new Exception("Tweet not found");
        }

        comment.setUser(comment.getUser());
        comment.setCommentUrl(comment.getCommentUrl());
        comment.setTweet(comment.getTweet());
        comment.setMedia(comment.getMedia());
        comment.setContent(comment.getContent());
        comment.setCommentCount(comment.getCommentCount());
        comment.setRetweetCount(comment.getRetweetCount());
        comment.setLikeCount(comment.getLikeCount());

        tweet.get().getComment().add(comment);

        return commentRepository.save(comment);
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet findTweetById(String tweetId) throws Exception {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new Exception("Tweet by Id: [" + tweetId + "] was not found."));
    }

    public void deleteTweet(String tweetId) {
        tweetRepository.deleteById(tweetId);
    }
}
