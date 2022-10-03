package com.jelaniak.twittercloneproject.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jelaniak.twittercloneproject.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

}
