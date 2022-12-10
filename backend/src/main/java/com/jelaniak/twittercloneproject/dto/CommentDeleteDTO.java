package com.jelaniak.twittercloneproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteDTO {
    private ObjectId parentTweetId;
    private ObjectId commentId;
}
