package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;

public class CommentUtility {
    public static Comment getNewComment(int number, User user, Tweet tweet, Media media) {
        Comment comment = new Comment();

        comment.setCommentId(new ObjectId());
        comment.setCommentUrl("http://www.commment" + number + ".co.uk/example");
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setMedia(media);
        comment.setContent("Content " + number);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setCommentCount(comment.getCommentCount());
        comment.setRetweetCount(comment.getRetweetCount());
        comment.setLikeCount(comment.getLikeCount());

        return comment;
    }
}
