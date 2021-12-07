package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
}
