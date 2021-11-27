package com.jelaniak.twittercloneproject.tweet;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tweet")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTweet(Tweet tweet) {
        tweetService.createTweet(tweet);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void getTweetById(@PathVariable String id) {
        tweetService.findTweetById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void getAllTweets() {
        tweetService.findAllTweets();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTweet(@PathVariable String id) {
        tweetService.deleteTweet(id);
    }
}
