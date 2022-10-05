package com.jelaniak.twittercloneproject.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.service.TweetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) {
        return new ResponseEntity<>(tweetService.createTweet(tweet), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Tweet> getTweetById(@PathVariable ObjectId id) throws Exception {
        return new ResponseEntity<>(tweetService.findTweetById(id), HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET)
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return new ResponseEntity<>(tweetService.findAllTweets(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public void deleteTweet(@PathVariable ObjectId id) {
        tweetService.deleteTweet(id);
    }
}
