package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, ObjectId> {

    Optional<ConfirmationToken> findByConfirmationTokenId(ObjectId confirmationTokenId);
}
