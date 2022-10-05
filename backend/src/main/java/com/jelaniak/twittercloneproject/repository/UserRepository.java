package com.jelaniak.twittercloneproject.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.jelaniak.twittercloneproject.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsernameAndEmail(String username, String email);

    User findByEmailAndPassword(String email, String password);

    User findByUsernameAndPassword(String username, String password);

    User deleteByUserId(ObjectId userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndEmail(String username, String email);
}
