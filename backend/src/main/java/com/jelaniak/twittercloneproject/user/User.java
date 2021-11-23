package com.jelaniak.twittercloneproject.user;

import com.jelaniak.twittercloneproject.tweet.Tweet;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String userId;
    private String username; // Login name
    private String password;
    private String email;
    private String displayName; // (Name above userHandle)
    private String userHandle; // (@ExampleName)
    private String bioLocation;
    private String bioExternalLink;
    private String bioText;
    private Instant createdDate;
    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;
    private String follow;
    private Set<String> following;
    private Set<String> followers;
    private Set<String> followersMutual;
    private List<Tweet> tweets;
    private Integer tweetCount;
    private Integer tweetQuoteCount;
    private boolean enabled;


}
