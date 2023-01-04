package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.CommentDTO;
import com.jelaniak.twittercloneproject.dto.request.DeleteCommentDTO;
import com.jelaniak.twittercloneproject.dto.request.TweetDTO;
import com.jelaniak.twittercloneproject.exception.comment.CommentNotFoundException;
import com.jelaniak.twittercloneproject.exception.tweet.TweetNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.*;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static com.jelaniak.twittercloneproject.utils.Helper.getTimeNow;

@Slf4j
@Service
public class TweetService {
    private final UserService userService;
    private final MediaService mediaService;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TweetService(
            UserService userService,
            MediaService mediaService,
            TweetRepository tweetRepository,
            CommentRepository commentRepository) {
        this.userService = userService;
        this.mediaService = mediaService;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    public Tweet createTweet(String tweetJSON, MultipartFile file) throws UserNotFoundException, IOException {
        double startTimer = System.nanoTime();
        Tweet tweet = new Tweet();
        String createMessage = "Create Tweet";
        JSONObject tweetJSONObject = new JSONObject(tweetJSON);

        tweet.setTweetId(new ObjectId());
        tweet.setUserId(new ObjectId(tweetJSONObject.get("userId").toString()));
        String content = tweetJSONObject.has("content")
                ? tweetJSONObject.get("content").toString()
                : null;
        tweet.setContent(content);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComments(new HashSet<>());
        tweet.setRetweetCount(0);
        tweet.setCommentCount(0);
        tweet.setLikeCount(0);
        tweet.setTweetType(TweetType.valueOf(tweetJSONObject.get("tweetType").toString()));

        boolean hasMedia = file != null;
        if (hasMedia) {
            createMessage += " with Media";
            Media media = mediaService.uploadMedia(file);
            tweet.setMedia(media);
        }

        User user = userService.findByUserId(tweet.getUserId());
        user.getTweets().add(tweet);
        user.setTweetCount(user.getTweets().size());
        userService.saveUser(user);

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + createMessage + ": Request completed in: " + secondsTaken + " seconds");

        return tweetRepository.save(tweet);
    }

    public Comment createComment(String commentJSON, MultipartFile file)
    throws IOException, UserNotFoundException, TweetNotFoundException {
        double startTimer = System.nanoTime();
        Comment comment = new Comment();
        String createMessage = "Create Comment";
        JSONObject commentJSONObject = new JSONObject(commentJSON);

        Tweet tweet = findByTweetId(new ObjectId(commentJSONObject.get("parentTweetId").toString()));

        comment.setCommentId(new ObjectId());
        comment.setParentTweetId(tweet.getTweetId());
        comment.setUserId(new ObjectId(commentJSONObject.get("userId").toString()));
        String content = commentJSONObject.has("content")
                ? commentJSONObject.get("content").toString()
                : null;
        comment.setContent(content);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setCommentCount(0);
        comment.setRetweetCount(0);
        comment.setLikeCount(0);
        comment.setTweetType(TweetType.valueOf(commentJSONObject.get("tweetType").toString()));

        boolean hasMedia = file != null;
        if (hasMedia) {
            createMessage += " with Media";
            Media media = mediaService.uploadMedia(file);
            comment.setMedia(media);
        }

        tweet.getComments().add(comment);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        User user = userService.findByUserId(comment.getUserId());
        user.getComments().add(comment);
        user.setCommentCount(user.getComments().size());
        userService.saveUser(user);

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + createMessage + ": Request completed in: " + secondsTaken + " seconds");

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

    public void deleteTweet(TweetDTO data) throws TweetNotFoundException, UserNotFoundException, IOException {
        double startTimer = System.nanoTime();

        // Remove associated comments and their media if present
        Tweet tweet = findByTweetId(data.getTweetId());
        tweet.getComments().forEach(comment -> {
            try {
                User user = userService.findByUserId(comment.getUserId());

                Comment commentStoredInUser = user.getComments().stream().filter(commentIndex ->
                        commentIndex.getCommentId().equals(comment.getCommentId())
                ).toList().get(0);

                boolean hasMedia = comment.getMedia() != null;
                if (hasMedia) {
                    mediaService.deleteMedia(comment.getMedia());
                    log.info(getTimeNow() + "Removed Media from comment");
                }

                user.getComments().remove(commentStoredInUser);
                commentRepository.delete(comment);
                user.setCommentCount(user.getComments().size());
                userService.saveUser(user);

                log.info(getTimeNow() + "Removed comment by '" + user.getUsername() + "', ID: '" + comment.getCommentId() + "'");
            } catch (UserNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Remove the tweet stored in the user, and the original tweet and media if present
        User user = userService.findByUserId(data.getUserId());

        Tweet tweetStoredInUser = user.getTweets().stream().filter(tweetIndex ->
                tweetIndex.getTweetId().equals(tweet.getTweetId())
        ).toList().get(0);

        boolean hasMedia = tweet.getMedia() != null;
        if (hasMedia) {
            log.info(getTimeNow() + "Removed Media from Tweet");
            mediaService.deleteMedia(tweet.getMedia());
        }

        user.getTweets().remove(tweetStoredInUser);
        user.setTweetCount(user.getTweets().size());
        userService.saveUser(user);
        tweetRepository.deleteById(data.getTweetId());

        log.info(getTimeNow() + "Removed tweet by '" + user.getUsername() + "', ID: '" + tweet.getTweetId() + "'");

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + "Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    public void deleteComment(DeleteCommentDTO data) throws TweetNotFoundException, UserNotFoundException, IOException {
        double startTimer = System.nanoTime();

        // Remove comment from tweet
        Tweet tweet = findByTweetId(data.getParentTweetId());

        Comment commentStoredInTweet = tweet.getComments().stream().filter(commentIndex ->
                commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        boolean commentInTweetHasMedia = commentStoredInTweet.getMedia() != null;
        if (commentInTweetHasMedia) {
            mediaService.deleteMedia(commentStoredInTweet.getMedia());
            log.info(getTimeNow() + "Removed Media from comment");
        }

        tweet.getComments().remove(commentStoredInTweet);
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        // Remove comment from user
        User user = userService.findByUserId(data.getUserId());

        Comment commentStoredInUser = user.getComments().stream().filter(commentIndex ->
                commentIndex.getCommentId().equals(data.getCommentId())
        ).toList().get(0);

        boolean commentStoredInUserHasMedia = commentStoredInUser.getMedia() != null;
        if (commentStoredInUserHasMedia) {
            mediaService.deleteMedia(commentStoredInUser.getMedia());
            log.info(getTimeNow() + "Removed Media from comment");
        }

        user.getComments().remove(commentStoredInUser);
        user.setCommentCount(user.getComments().size());
        commentRepository.deleteById(data.getCommentId());
        userService.saveUser(user);

        log.info(getTimeNow() + "Removed comment by '" + user.getUsername() + "', ID: '" + data.getCommentId() + "'");

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + "Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    public void likeTweet(TweetDTO data) throws TweetNotFoundException, UserNotFoundException {
        Tweet tweet = findByTweetId(data.getTweetId());
        User user = userService.findByUserId(data.getUserId());

        boolean alreadyLiked = user.getLikedTweets().contains(tweet);
        if (alreadyLiked) {
            tweet.setLikeCount(tweet.getLikeCount() - 1);
            tweetRepository.save(tweet);

            Tweet likedTweet = user.getLikedTweets().stream().filter(likedTweetIndex ->
                    likedTweetIndex.getTweetId().equals(data.getTweetId())
            ).toList().get(0);

            user.getLikedTweets().remove(likedTweet);
            userService.saveUser(user);
        } else {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
            tweetRepository.save(tweet);

            user.getLikedTweets().add(tweet);
            userService.saveUser(user);
        }
    }

    public void likeComment(CommentDTO data) throws UserNotFoundException, CommentNotFoundException {
        Comment comment = findByCommentId(data.getCommentId());
        User user = userService.findByUserId(data.getUserId());

        boolean alreadyLiked = user.getLikedComments().contains(comment);
        if (alreadyLiked) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentRepository.save(comment);

            user.getLikedComments().remove(comment);
            userService.saveUser(user);
        } else {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);

            user.getLikedComments().add(comment);
            userService.saveUser(user);
        }
    }
}
