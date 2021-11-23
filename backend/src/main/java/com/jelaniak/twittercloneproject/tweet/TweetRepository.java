package com.jelaniak.twittercloneproject.tweet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

}
