package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class CommentDTO {
    private ObjectId commentId;
    private ObjectId userId;
}
