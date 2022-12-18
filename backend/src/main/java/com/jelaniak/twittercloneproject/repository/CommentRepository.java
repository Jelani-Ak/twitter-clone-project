package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    void deleteByCommentId(ObjectId commentId);
}
