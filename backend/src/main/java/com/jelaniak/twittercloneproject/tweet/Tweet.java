package com.jelaniak.twittercloneproject.tweet;

import com.jelaniak.twittercloneproject.user.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String createdDate;
    private Integer likeCount;
    private TweetType tweetType;
}
