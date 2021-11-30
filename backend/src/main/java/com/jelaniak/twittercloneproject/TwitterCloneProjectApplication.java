package com.jelaniak.twittercloneproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class TwitterCloneProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterCloneProjectApplication.class, args);
    }
}
