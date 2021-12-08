package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Tweet")
public class Tweet {

    @Id
    private String tweetId;
    @DBRef
    private User user;
    private String url;
    private String content;
    private String mediaUrl;
    private Integer commentCount;
    private Integer retweetCount;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer likeCount;
    private TweetType tweetType;
}

