package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, ObjectId> {

    Optional<ConfirmationToken> findByConfirmationTokenId(ObjectId confirmationTokenId);

    @Query("UPDATE ConfirmationToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(ObjectId token, LocalDateTime confirmedAt);
}
