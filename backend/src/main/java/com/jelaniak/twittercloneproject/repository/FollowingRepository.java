package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, String> {
}
