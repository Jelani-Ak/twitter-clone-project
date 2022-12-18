package com.jelaniak.twittercloneproject.controller;

import java.util.List;

import com.jelaniak.twittercloneproject.dto.request.TweetDeleteDTO;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.service.TweetService;

@RestController
@RequestMapping(value = "/api/v1/tweet")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping(
            value = "/create-tweet",
            method = RequestMethod.POST)
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) throws UserNotFoundException {
        return new ResponseEntity<>(tweetService.createTweet(tweet), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/delete-tweet",
            method = RequestMethod.DELETE)
    public void deleteTweet(@RequestBody TweetDeleteDTO data) {
        tweetService.deleteTweet(data);
    }

    @RequestMapping(
            value = "/{tweetId}",
            method = RequestMethod.GET)
    public ResponseEntity<Tweet> getTweetById(@PathVariable ObjectId tweetId) throws Exception {
        return new ResponseEntity<>(tweetService.findByTweetId(tweetId), HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/get-all-tweets",
            method = RequestMethod.GET)
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return new ResponseEntity<>(tweetService.findAllTweets(), HttpStatus.OK);
    }
}
