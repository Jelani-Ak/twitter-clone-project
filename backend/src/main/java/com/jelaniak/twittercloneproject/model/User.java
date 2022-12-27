package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document(value = "User")
public class User {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
    private String displayName;
    private String userHandleName;
    private String bioAboutText;
    private String bioLocation;
    private String bioExternalLink;

    @DBRef private Set<Role> roles;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCreation;

    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;

    @DBRef private Set<User> usersYouFollow;
    @DBRef private Set<User> usersFollowingYou;
    @DBRef private Set<User> mutualFollowers;

    @DBRef private Set<Tweet> tweets;
    @DBRef private Set<Tweet> likedTweets;
    @DBRef private Set<Comment> comments;
    @DBRef private Set<Comment> likedComments;

    private int usersYouFollowCount;
    private int usersFollowingYouCount;
    private int mutualFollowersCount;

    private int tweetCount;
    private int likedTweetCount;
    private int commentCount;
    private int likedCommentCount;

    private boolean following; // TODO: 26/12/2022 - Remove
    private boolean verified;
    private boolean locked;
    private boolean enabled;
}
