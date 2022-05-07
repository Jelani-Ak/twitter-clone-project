package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;

    public TweetService(
            TweetRepository tweetRepository,
            CommentRepository commentRepository
    ) {
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    public Tweet createTweet(Tweet tweet) {
        tweet.setTweetId(new ObjectId());
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

    public Comment createTweetComment(ObjectId tweetId, Comment comment) throws Exception {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);

        if (tweet.isEmpty()) {
            throw new Exception("Tweet not found");
        }

        comment.setCommentId(new ObjectId());
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

    public Tweet findTweetById(ObjectId tweetId) throws Exception {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new Exception("Tweet by Id: [" + tweetId + "] was not found."));
    }

    public void deleteTweet(ObjectId tweetId) {
        tweetRepository.deleteById(tweetId);
    }
}
