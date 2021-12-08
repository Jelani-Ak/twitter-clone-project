package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tweet")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestAttribute User user, @RequestBody Tweet tweet) {
        return tweetService.createTweet(user, tweet);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet getTweetById(@PathVariable String id) {
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
