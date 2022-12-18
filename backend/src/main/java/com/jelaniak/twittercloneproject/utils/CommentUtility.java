package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;

public class CommentUtility {
    public static Comment getNewComment(int number, ObjectId userId, Tweet tweet, Media media) {
        Comment comment = new Comment();

        comment.setCommentId(new ObjectId());
        comment.setUserId(userId);
        comment.setParentTweetId(tweet.getTweetId());
        comment.setMedia(media);
        comment.setContent("Content " + number);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setCommentCount(comment.getCommentCount());
        comment.setRetweetCount(comment.getRetweetCount());
        comment.setLikeCount(comment.getLikeCount());

        return comment;
    }
}
