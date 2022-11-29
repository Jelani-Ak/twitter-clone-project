package com.jelaniak.twittercloneproject.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jelaniak.twittercloneproject.dto.TweetAndCommentIdDTO;
import com.jelaniak.twittercloneproject.exception.CommentNotFoundException;
import com.jelaniak.twittercloneproject.exception.TweetNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet createTweet(Tweet tweet) {
        tweet.setTweetId(tweet.getTweetId());
        tweet.setUser(tweet.getUser());  // TODO: Remove? Change to userId?
        tweet.setMedia(tweet.getMedia());  // TODO: Remove?
        tweet.setContent(tweet.getContent());  // TODO: Remove?
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>()); // TODO - Hold every comment object? or every comment id and fetch it?
        tweet.setRetweetCount(0);
        tweet.setCommentCount(0);
        tweet.setLikeCount(0);
        tweet.setTweetType(tweet.getTweetType());

        return tweetRepository.save(tweet);
    }

    public Comment createComment(Comment comment) throws Exception {
        Tweet tweet = findByTweetId(comment.getParentTweetId());

        comment.setCommentId(new ObjectId());
        comment.setParentTweetId(comment.getParentTweetId());
        comment.setUser(comment.getUser()); // TODO: Remove? Change to userId?
        comment.setMedia(comment.getMedia());  // TODO: Remove?
        comment.setContent(comment.getContent());  // TODO: Remove?
        comment.setRetweetCount(0);
        comment.setCommentCount(0);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setLikeCount(0);

        tweet.getComments().add(comment);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        return comment;
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet findByTweetId(ObjectId tweetId) throws TweetNotFoundException {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet by Id: [" + tweetId + "] was not found."));
    }

    @Transactional
    public void deleteTweet(ObjectId tweetId) {
        tweetRepository.deleteByTweetId(tweetId);
    }

    @Transactional
    public void deleteComment(TweetAndCommentIdDTO data) throws TweetNotFoundException {
        Tweet tweet = findByTweetId(data.getParentTweetId());

        Comment commentFound = tweet.getComments().stream().filter(commentIndex ->
            commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        tweet.getComments().remove(commentFound);
        tweet.setCommentCount(tweet.getComments().size());

        tweetRepository.save(tweet);
    }
}
