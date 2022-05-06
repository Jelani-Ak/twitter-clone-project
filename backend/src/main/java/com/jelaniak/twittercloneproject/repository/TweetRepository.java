package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Tweet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, ObjectId> {

}
