package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "User")
public class User {

    @Id
    private String userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
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
    private boolean locked;
    private boolean enabled;

    public User(String username, String email, String encode) {

    }
}
