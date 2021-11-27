package com.jelaniak.twittercloneproject.tweet;

import com.jelaniak.twittercloneproject.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Calendar;

@Document(value = "Tweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String tweetId;
    private User user;
    private String url;
    @NonNull
    private String text;
    private String mediaUrl;
    private Integer commentCount;
    private Integer retweetCount;
    private Calendar createdDate;
    private Integer likeCount;
    private TweetType tweetType;
}
