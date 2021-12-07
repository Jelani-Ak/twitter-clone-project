package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
