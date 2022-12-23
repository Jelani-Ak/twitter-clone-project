package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class CommentDeleteDTO {
    private ObjectId parentTweetId;
    private ObjectId commentId;
    private ObjectId userId;
}
