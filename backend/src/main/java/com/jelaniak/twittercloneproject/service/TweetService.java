package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.CommentDeleteDTO;
import com.jelaniak.twittercloneproject.dto.request.LikeTweetDTO;
import com.jelaniak.twittercloneproject.dto.request.TweetDeleteDTO;
import com.jelaniak.twittercloneproject.exception.comment.CommentNotFoundException;
import com.jelaniak.twittercloneproject.exception.tweet.TweetNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class TweetService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

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
        double startTimerOne = System.nanoTime();

        tweet.setTweetId(new ObjectId());
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setRetweetCount(0);
        tweet.setCommentCount(0);
        tweet.setLikeCount(0);

        User user = userService.findByUserId(tweet.getUserId());
        user.getTweets().add(tweet);
        user.setTweetCount(user.getTweets().size());
        userRepository.save(user);

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimerOne) / 1_000_000_000);
        LOGGER.info("Create Tweet: Request completed in: " + secondsTaken + " seconds");

        return tweetRepository.save(tweet);
    }

    public Comment createComment(Comment comment) throws Exception {
        double startTimerOne = System.nanoTime();

        Tweet tweet = findByTweetId(comment.getParentTweetId());

        comment.setCommentId(new ObjectId());
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setRetweetCount(0);
        comment.setCommentCount(0);
        comment.setLikeCount(0);

        tweet.getComments().add(comment);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        User user = userService.findByUserId(comment.getUserId());
        user.getComments().add(comment);
        user.setCommentCount(user.getComments().size());
        userRepository.save(user);

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimerOne) / 1_000_000_000);
        LOGGER.info("Create Comment: Request completed in: " + secondsTaken + " seconds");

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

    public Comment findByCommentId(ObjectId commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment by Id: [" + commentId + "] was not found"));
        return comment;
    }

    public void deleteTweet(TweetDeleteDTO data) throws TweetNotFoundException, UserNotFoundException {
        double startTimerOne = System.nanoTime();

        Tweet tweet = findByTweetId(data.getTweetId());
        tweet.getComments().forEach(comment -> {
            try {
                User user = userService.findByUserId(comment.getUserId());

                Comment commentStoredInUser = user.getComments().stream().filter(commentIndex ->
                        commentIndex.getCommentId().equals(comment.getCommentId())
                ).toList().get(0);

                user.getComments().remove(commentStoredInUser);
                commentRepository.delete(comment);
                user.setCommentCount(user.getComments().size());

                userRepository.save(user);
                LOGGER.info("Removed comment by '" + user.getUsername() + "', ID: '" + comment.getCommentId() + "'");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        User user = userService.findByUserId(data.getUserId());

        Tweet tweetStoredInUser = user.getTweets().stream().filter(tweetIndex ->
                tweetIndex.getTweetId().equals(tweet.getTweetId())
        ).toList().get(0);

        user.getTweets().remove(tweetStoredInUser);
        user.setTweetCount(user.getTweets().size());
        userRepository.save(user);

        tweetRepository.deleteById(data.getTweetId());
        LOGGER.info("Removed tweet by '" + user.getUsername() + "', ID: '" + tweet.getTweetId() + "'");

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimerOne) / 1_000_000_000);
        LOGGER.info("Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    public void deleteComment(CommentDeleteDTO data) throws TweetNotFoundException, UserNotFoundException {
        double startTimerOne = System.nanoTime();

        Tweet tweet = findByTweetId(data.getParentTweetId());

        Comment commentStoredInTweet = tweet.getComments().stream().filter(commentIndex ->
                commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        tweet.getComments().remove(commentStoredInTweet);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        User user = userService.findByUserId(data.getUserId());

        Comment commentStoredInUser = user.getComments().stream().filter(commentIndex ->
                commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        user.getComments().remove(commentStoredInUser);
        user.setCommentCount(user.getComments().size());
        commentRepository.deleteById(data.getCommentId());

        LOGGER.info("Removed comment by '" + user.getUsername() + "', ID: '" + data.getCommentId() + "'");

        userRepository.save(user);

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimerOne) / 1_000_000_000);
        LOGGER.info("Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    public void likeTweet(LikeTweetDTO data) throws TweetNotFoundException, UserNotFoundException {
        Tweet tweet = findByTweetId(data.getTweetId());
        User user = userService.findByUserId(data.getUserId());

        boolean alreadyLiked = user.getLikedTweets().contains(tweet);
        if (alreadyLiked) {
            tweet.setLikeCount(tweet.getLikeCount() - 1);
            tweetRepository.save(tweet);

            user.getLikedTweets().remove(tweet);
            userRepository.save(user);
        } else {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
            tweetRepository.save(tweet);

            user.getLikedTweets().add(tweet);
            userRepository.save(user);
        }
    }
}
