package com.jelaniak.twittercloneproject.tweet;

import com.jelaniak.twittercloneproject.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Tweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String tweetId;
    private User username;
    private String url;
    @NonNull
    private String content;
    private String mediaUrl;
    private Integer commentCount;
    private Integer retweetCount;
    private String createdDate;
    private Integer likeCount;
    private TweetType tweetType;
}
