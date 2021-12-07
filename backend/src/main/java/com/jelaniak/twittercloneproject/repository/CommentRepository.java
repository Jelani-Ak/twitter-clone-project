package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
