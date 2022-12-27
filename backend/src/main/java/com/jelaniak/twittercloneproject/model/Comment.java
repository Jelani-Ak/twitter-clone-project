package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Document(value = "Comment")
public class Comment {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId parentTweetId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private Media media;

    @NotBlank
    @Size(max = 280)
    private String content;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCreation;

    private Integer commentCount; // TODO: 23/12/2022 - Remove?
    private Integer retweetCount;
    private Integer likeCount;

    private TweetType tweetType;
}
