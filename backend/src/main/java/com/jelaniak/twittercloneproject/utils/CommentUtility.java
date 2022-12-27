package com.jelaniak.twittercloneproject.utils;

import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Media;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class CommentUtility {
    public static Comment getNewComment(int number) {
        Comment comment = new Comment();

        comment.setCommentId(new ObjectId());
        comment.setUserId(new ObjectId());
        comment.setParentTweetId(new ObjectId());
        comment.setMedia(new Media());
        comment.setContent("Content " + number);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setCommentCount(comment.getCommentCount()); // TODO: 23/12/2022 - Remove?
        comment.setRetweetCount(number);
        comment.setLikeCount(number);

        return comment;
    }
}
