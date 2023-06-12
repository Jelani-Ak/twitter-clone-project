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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        setTweetProperties(tweet, tweetJSONObject);

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

    private void setTweetProperties(Tweet tweet, JSONObject tweetJSONObject) {
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
    }

    public Comment createComment(String commentJSON, MultipartFile file)
            throws IOException, UserNotFoundException, TweetNotFoundException {
        double startTimer = System.nanoTime();
        Comment comment = new Comment();
        String createMessage = "Create Comment";
        JSONObject commentJSONObject = new JSONObject(commentJSON);

        Tweet tweet = findByTweetId(new ObjectId(commentJSONObject.get("parentTweetId").toString()));

        setCommentProperties(comment, commentJSONObject);

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

    private void setCommentProperties(Comment comment, JSONObject commentJSONObject) {
        comment.setCommentId(new ObjectId());
        comment.setParentTweetId(new ObjectId(commentJSONObject.get("parentTweetId").toString()));
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
    }

    public void deleteTweet(TweetDTO data) throws TweetNotFoundException, UserNotFoundException, IOException {
        double startTimer = System.nanoTime();

        Tweet tweet = findByTweetId(data.getTweetId());
        deleteAssociatedComments(tweet.getComments());

        User user = userService.findByUserId(data.getUserId());
        user.getTweets().removeIf(t -> t.getTweetId().equals(data.getTweetId()));
        user.setTweetCount(user.getTweets().size());
        userService.saveUser(user);
        deleteMediaIfPresent(tweet.getMedia());

        tweetRepository.deleteById(data.getTweetId());

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + "Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    public void deleteComment(DeleteCommentDTO data) throws TweetNotFoundException, UserNotFoundException, IOException {
        double startTimer = System.nanoTime();

        Tweet tweet = findByTweetId(data.getParentTweetId());
        Comment comment = findCommentById(tweet.getComments(), data.getCommentId());

        deleteMediaIfPresent(comment.getMedia());

        tweet.getComments().removeIf(tweetComment -> tweetComment.getCommentId().equals(data.getCommentId()));
        tweet.setCommentCount(tweet.getComments().size());
        tweetRepository.save(tweet);

        User user = userService.findByUserId(data.getUserId());
        user.getComments().removeIf(userComment -> userComment.getCommentId().equals(data.getCommentId()));
        user.setCommentCount(user.getComments().size());
        userService.saveUser(user);

        commentRepository.deleteById(data.getCommentId());

        double endTimer = System.nanoTime();
        double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
        log.info(getTimeNow() + "Delete Tweet: Request completed in: " + secondsTaken + " seconds");
    }

    private void deleteAssociatedComments(Set<Comment> comments) throws UserNotFoundException, IOException {
        for (Comment comment : comments) {
            User user = userService.findByUserId(comment.getUserId());

            Comment commentStoredInUser = user.getComments().stream()
                    .filter(commentIndex -> commentIndex.getCommentId().equals(comment.getCommentId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Comment not found in user's comments"));

            deleteMediaIfPresent(comment.getMedia());

            user.getComments().remove(commentStoredInUser);
            user.setCommentCount(user.getComments().size());
            userService.saveUser(user);

            log.info(getTimeNow() + "Removed comment by '" + user.getUsername() + "', ID: '" + comment.getCommentId() + "'");
        }
    }

    private void deleteMediaIfPresent(Media media) throws IOException {
        if (media != null) {
            mediaService.deleteMedia(media);
            log.info(getTimeNow() + "Removed Media");
        }
    }

    private Comment findCommentById(Set<Comment> comments, ObjectId commentId) {
        return comments.stream()
                .filter(c -> c.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public List<Tweet> findAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        return tweets;
    }

    // TODO: 05/01/2023 - Create another similar function that returns a Tweet[] pageable:
    //  - belonging to the current logged in user
    //  - belonging to a user's profile
    //  - a concatenation of followed users
    // This returns every single tweet in the repository as a pageable
    public Page<Tweet> findAllTweetsAsPageable(Pageable pageable) {
        Page<Tweet> tweets = tweetRepository.findAll(pageable);
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


    public void likeTweet(TweetDTO data) throws TweetNotFoundException, UserNotFoundException {
        Tweet tweet = findByTweetId(data.getTweetId());
        User user = userService.findByUserId(data.getUserId());

        boolean alreadyLiked = user.getLikedTweets().contains(tweet);
        if (alreadyLiked) {
            tweet.setLikeCount(tweet.getLikeCount() - 1);
            Tweet likedTweet = user.getLikedTweets().stream()
                    .filter(likedTweetIndex -> likedTweetIndex.getTweetId().equals(data.getTweetId()))
                    .findFirst()
                    .orElseThrow(() -> new TweetNotFoundException("Liked tweet not found"));

            user.getLikedTweets().remove(likedTweet);
        } else {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
            user.getLikedTweets().add(tweet);
        }

        tweetRepository.save(tweet);
        userService.saveUser(user);
    }

    public void likeComment(CommentDTO data) throws UserNotFoundException, CommentNotFoundException {
        Comment comment = findByCommentId(data.getCommentId());
        User user = userService.findByUserId(data.getUserId());

        boolean alreadyLiked = user.getLikedComments().contains(comment);
        if (alreadyLiked) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            user.getLikedComments().remove(comment);
        } else {
            comment.setLikeCount(comment.getLikeCount() + 1);
            user.getLikedComments().add(comment);
        }

        commentRepository.save(comment);
        userService.saveUser(user);
    }
}
