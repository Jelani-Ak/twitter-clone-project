package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, String> {

}
