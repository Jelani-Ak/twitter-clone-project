package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, String> {
}
