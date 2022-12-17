package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.CommentDeleteDTO;
import com.jelaniak.twittercloneproject.exception.TweetNotFoundException;
import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/comment")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CommentController {

    private final TweetService tweetService;

    @Autowired
    public CommentController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping(
            value = "/create-comment",
            method = RequestMethod.POST)
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws Exception {
        return new ResponseEntity<>(tweetService.createComment(comment), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/delete-comment",
            method = RequestMethod.DELETE)
    public void deleteComment(@RequestBody CommentDeleteDTO data) throws TweetNotFoundException {
        tweetService.deleteComment(data);
    }
}
