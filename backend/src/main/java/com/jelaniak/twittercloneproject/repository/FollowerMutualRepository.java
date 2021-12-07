package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.FollowerMutual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerMutualRepository extends JpaRepository<FollowerMutual, String> {
}
