package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

}