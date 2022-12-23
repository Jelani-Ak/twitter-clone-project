package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class LikeTweetDTO {
    private ObjectId tweetId;
    private ObjectId userId;
}
