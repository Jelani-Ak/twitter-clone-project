package com.jelaniak.twittercloneproject.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import com.jelaniak.twittercloneproject.dto.request.CommentDeleteDTO;
import com.jelaniak.twittercloneproject.dto.request.TweetDeleteDTO;
import com.jelaniak.twittercloneproject.exception.comment.CommentNotFoundException;
import com.jelaniak.twittercloneproject.exception.tweet.TweetNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.repository.TweetRepository;

@Service
public class TweetService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TweetService(
            UserService userService,
            UserRepository userRepository,
            TweetRepository tweetRepository,
            CommentRepository commentRepository
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    public Tweet createTweet(Tweet tweet) throws UserNotFoundException {
        tweet.setTweetId(new ObjectId());
        tweet.setUserId(tweet.getUserId()); //TODO: Remove?
        tweet.setMedia(tweet.getMedia()); //TODO: Remove?
        tweet.setContent(tweet.getContent()); //TODO: Remove?
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setRetweetCount(0);
        tweet.setCommentCount(0);
        tweet.setLikeCount(0);
        tweet.setTweetType(tweet.getTweetType()); //TODO: Remove?

        User user = userService.findByUserId(tweet.getUserId());
        user.getTweets().add(tweet);
        user.setTweetCount(user.getTweets().size());
        userRepository.save(user);

        return tweetRepository.save(tweet);
    }

    public Comment createComment(Comment comment) throws Exception {
        Tweet tweet = findByTweetId(comment.getParentTweetId());

        comment.setCommentId(new ObjectId());
        comment.setParentTweetId(comment.getParentTweetId()); //TODO: Remove?
        comment.setUserId(comment.getUserId()); // TODO: Remove?
        comment.setMedia(comment.getMedia());  // TODO: Remove?
        comment.setContent(comment.getContent());  // TODO: Remove?
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setRetweetCount(0);
        comment.setCommentCount(0);
        comment.setLikeCount(0);

        tweet.getComments().add(comment);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        return commentRepository.save(comment);
    }

    public List<Tweet> findAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        return tweets;
    }

    public Tweet findByTweetId(ObjectId tweetId) throws TweetNotFoundException {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet by Id: [" + tweetId + "] was not found"));
        return tweet;
    }

    public void deleteTweet(TweetDeleteDTO data) {
        ObjectId tweetId = data.getTweetId();
        tweetRepository.deleteByTweetId(tweetId);
    }

    public void deleteComment(CommentDeleteDTO data) throws TweetNotFoundException {
        Tweet tweet = findByTweetId(data.getParentTweetId());

        Comment commentFound = tweet.getComments().stream().filter(commentIndex ->
                commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        tweet.getComments().remove(commentFound);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        commentRepository.delete(commentFound);
    }
}
