package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username) throws UsernameNotFoundException;

    Optional<User> findByEmail(String email);

    User findByUsernameAndEmail(String username, String email); // TODO: 23/12/2022 - Remove?

    User findByEmailAndPassword(String email, String password); // TODO: 23/12/2022 - Remove?

    User findByUsernameAndPassword(String username, String password); // TODO: 23/12/2022 - Remove?

    void deleteByUserId(ObjectId userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndEmail(String username, String email); // TODO: 23/12/2022 - Remove?
}
