package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweet")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestAttribute User userId, @RequestBody Tweet tweet) {
        return tweetService.createTweet(userId, tweet);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet getTweetById(@PathVariable String id) throws Exception {
        return tweetService.findTweetById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> getAllTweets() {
        return tweetService.findAllTweets();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTweet(@PathVariable String id) {
        tweetService.deleteTweet(id);
    }
}
