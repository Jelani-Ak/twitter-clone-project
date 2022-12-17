package com.jelaniak.twittercloneproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetDeleteDTO {
    private ObjectId tweetId;
}
