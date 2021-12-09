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
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "User")
public class User {

    @Id
    private String userId;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String userHandle; // (@ExampleName)
    private String bioText;
    private String bioLocation;
    private String bioExternalLink;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;

    private String[] authorities;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @DBRef
    private Set<User> following = new HashSet<>();

    @DBRef
    private Set<User> followers = new HashSet<>();

    @DBRef
    private Set<User> followersMutual = new HashSet<>();

    @DBRef
    private List<Tweet> tweets = new ArrayList<>();

    private Integer tweetCount;
    private Integer tweetQuoteCount;

    private boolean follow;
    private boolean enabled;
}
