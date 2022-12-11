package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Role;
import com.jelaniak.twittercloneproject.model.RoleType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByRole(RoleType role);
}
