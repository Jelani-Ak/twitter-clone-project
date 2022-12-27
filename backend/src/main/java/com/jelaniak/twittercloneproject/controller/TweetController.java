package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.TweetDTO;
import com.jelaniak.twittercloneproject.exception.tweet.TweetNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.service.TweetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/tweet")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping(
            value = "/create-tweet",
            method = RequestMethod.POST)
    public ResponseEntity<?> createTweet(
            @RequestPart(value = "body") String tweetJSON,
            @RequestPart(required = false, value = "file") MultipartFile file) throws UserNotFoundException, IOException {
        return new ResponseEntity<>(tweetService.createTweet(tweetJSON, file), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/delete-tweet",
            method = RequestMethod.DELETE)
    public void deleteTweet(
            @RequestBody TweetDTO data) throws UserNotFoundException, TweetNotFoundException {
        tweetService.deleteTweet(data);
    }

    @RequestMapping(
            value = "/{tweetId}/get-tweet",
            method = RequestMethod.GET)
    public ResponseEntity<Tweet> getTweetById(
            @PathVariable ObjectId tweetId) throws Exception {
        return new ResponseEntity<>(tweetService.findByTweetId(tweetId), HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/get-all-tweets",
            method = RequestMethod.GET)
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return new ResponseEntity<>(tweetService.findAllTweets(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/like-tweet",
            method = RequestMethod.POST)
    public ResponseEntity<?> likeTweet(@RequestBody TweetDTO data) throws UserNotFoundException, TweetNotFoundException {
        tweetService.likeTweet(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
