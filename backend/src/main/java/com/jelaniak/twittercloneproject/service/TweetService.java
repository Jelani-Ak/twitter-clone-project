package com.jelaniak.twittercloneproject.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import com.jelaniak.twittercloneproject.exception.CommentNotFoundException;
import com.jelaniak.twittercloneproject.exception.TweetNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Tweet createTweet(Tweet tweet) {
        tweet.setTweetId(tweet.getTweetId());
        tweet.setTweetUrl(tweet.getTweetUrl());
        tweet.setUser(tweet.getUser());
        tweet.setMedia(tweet.getMedia());
        tweet.setContent(tweet.getContent());
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>()); // Should it hold the entire object or each id?
        tweet.setCommentCount(0);
        tweet.setRetweetCount(0);
        tweet.setLikeCount(0);
        tweet.setTweetType(tweet.getTweetType());

        return tweetRepository.save(tweet);
    }

    public Comment createComment(ObjectId tweetId, Comment comment) throws Exception {
        Tweet tweet = findByTweetId(tweetId);

        comment.setCommentId(comment.getCommentId());
        comment.setUser(comment.getUser());
        comment.setCommentUrl(comment.getCommentUrl());
        comment.setTweet(tweet); // TODO: Change from object to tweetId only
        comment.setMedia(comment.getMedia());
        comment.setContent(comment.getContent());
        comment.setCommentCount(0);
        comment.setRetweetCount(0);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setLikeCount(0);

        tweet.getComments().add(comment);

        return commentRepository.save(comment);
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet findByTweetId(ObjectId tweetId) throws TweetNotFoundException {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet by Id: [" + tweetId + "] was not found."));
    }

    public Comment findByCommentId(ObjectId commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Tweet by Id: [" + commentId + "] was not found."));
    }

    @Transactional
    public void deleteTweet(ObjectId tweetId) {
        tweetRepository.deleteByTweetId(tweetId);
    }

    @Transactional
    public void deleteComment(ObjectId commentId) throws CommentNotFoundException, TweetNotFoundException {
        Comment comment = findByCommentId(commentId);

        Tweet tweet = findByTweetId(comment.getTweet().getTweetId());
        tweet.getComments().remove(comment);

        tweetRepository.save(tweet);
        commentRepository.deleteByCommentId(commentId);
    }

}
