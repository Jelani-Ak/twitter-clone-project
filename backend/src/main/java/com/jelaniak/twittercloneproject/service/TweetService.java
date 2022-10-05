package com.jelaniak.twittercloneproject.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Tweet createTweet(Tweet tweet) {

        tweet.setTweetId(new ObjectId());
        tweet.setTweetUrl(tweet.getTweetUrl());
        tweet.setUser(tweet.getUser());
        tweet.setMedia(tweet.getMedia());
        tweet.setContent(tweet.getContent());
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setCommentCount(0);
        tweet.setRetweetCount(0);
        tweet.setLikeCount(0);
        tweet.setTweetType(tweet.getTweetType());

        return tweetRepository.save(tweet);
    }

    public Comment createComment(ObjectId tweetId, Comment comment) throws Exception {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);

        if (tweet.isEmpty()) {
            throw new Exception("Tweet not found");
        }

        comment.setCommentId(new ObjectId());
        comment.setUser(comment.getUser());
        comment.setCommentUrl(comment.getCommentUrl());

        if (tweetRepository.findById(tweetId).isPresent()) {
            comment.setTweet(tweetRepository.findById(tweetId).get());
        }

        comment.setMedia(comment.getMedia());
        comment.setContent(comment.getContent());
        comment.setCommentCount(comment.getCommentCount());
        comment.setRetweetCount(comment.getRetweetCount());
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setLikeCount(comment.getLikeCount());

        tweet.get().getComments().add(comment);

        return commentRepository.save(comment);
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet findTweetById(ObjectId tweetId) throws Exception {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new Exception("Tweet by Id: [" + tweetId + "] was not found."));
    }

    public Comment findCommentById(ObjectId commentId) throws Exception {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Tweet by Id: [" + commentId + "] was not found."));
    }

    public void deleteTweet(ObjectId tweetId) {
        tweetRepository.deleteById(tweetId);
    }
}
