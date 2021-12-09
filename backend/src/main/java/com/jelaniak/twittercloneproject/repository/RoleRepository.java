package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
