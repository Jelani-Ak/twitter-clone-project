package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document(value = "Tweet")
public class Tweet {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId tweetId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private Media media;

    @NotBlank
    @Size(max = 280)
    private String content;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCreation;

    @DBRef
    private Set<Comment> comments;

    private Integer commentCount;
    private Integer retweetCount;
    private Integer likeCount;

    private TweetType tweetType;
}

