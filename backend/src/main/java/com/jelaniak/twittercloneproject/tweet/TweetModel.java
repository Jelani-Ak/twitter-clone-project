package com.jelaniak.twittercloneproject.tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(value = "Tweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String tweetId;
    private String text;
    private String mediaUrl;
    private String author;
    private Integer commentCount;
    private Integer retweetCount;
    private Integer likeCount;
    private TweetType tweetType;
}
