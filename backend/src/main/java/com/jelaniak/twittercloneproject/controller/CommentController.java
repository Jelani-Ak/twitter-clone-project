package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.DeleteCommentDTO;
import com.jelaniak.twittercloneproject.exception.tweet.TweetNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    private final TweetService tweetService;

    @Autowired
    public CommentController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping(
            value = "/create-comment",
            method = RequestMethod.POST)
    public ResponseEntity<Comment> createComment(
            @RequestPart(value = "body") String commentJSON,
            @RequestPart(required = false, value = "file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(tweetService.createComment(commentJSON, file), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/delete-comment",
            method = RequestMethod.DELETE)
    public void deleteComment(@RequestBody DeleteCommentDTO data) throws TweetNotFoundException, UserNotFoundException, IOException {
        tweetService.deleteComment(data);
    }
}
