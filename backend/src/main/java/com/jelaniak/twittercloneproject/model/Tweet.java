package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jelaniak.twittercloneproject.enums.TweetType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(value = "Tweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    private String tweetId;
    private String username;
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

