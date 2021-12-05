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
import java.util.List;
import java.util.Set;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    private String username; // Login name
    private String password;
    private String email;
    private String displayName; // (Name above userHandle)
    private String userHandle; // (@ExampleName)
    private String bioText;
    private String bioLocation;
    private String bioExternalLink;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;
    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;
    private boolean follow;
    private Set<String> following;
    private Set<String> followers;
    private Set<String> followersMutual;

    @DBRef
    private List<Tweet> tweets;
    private Integer tweetCount;
    private Integer tweetQuoteCount;
    private boolean enabled;
}
