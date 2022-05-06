package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUserId(ObjectId userId);

    void deleteByUserId(ObjectId userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
