package com.jelaniak.twittercloneproject.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class TweetAndCommentIdDTO {

    private ObjectId parentTweetId;
    private ObjectId commentId;
}
