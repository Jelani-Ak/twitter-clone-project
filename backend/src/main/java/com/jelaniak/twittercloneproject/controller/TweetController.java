package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweet")
public class TweetController {
    @Autowired
    private final TweetService tweetService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.createTweet(tweet);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet getTweetById(@PathVariable ObjectId id) throws Exception {
        return tweetService.findTweetById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> getAllTweets() {
        return tweetService.findAllTweets();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTweet(@PathVariable ObjectId id) {
        tweetService.deleteTweet(id);
    }
}
