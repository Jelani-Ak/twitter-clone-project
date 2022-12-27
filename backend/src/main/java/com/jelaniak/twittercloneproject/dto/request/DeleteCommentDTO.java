package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class DeleteCommentDTO {
    private ObjectId parentTweetId;
    private ObjectId commentId;
    private ObjectId userId;
}
