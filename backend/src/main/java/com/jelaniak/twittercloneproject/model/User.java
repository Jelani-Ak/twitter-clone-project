package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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

    @DBRef
    private Set<Role> roles;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfCreation;

    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;

    private Set<User> usersYouFollow; //TODO: Create new Model, 'FollowedUsers'
    private Set<User> usersFollowingYou; // TODO: Create new Model, 'UsersFollowing'
    private Set<User> mutualFollowers; // TODO: Create new Model, 'MutualFollowers'
    private Set<Tweet> tweets;

    private Integer tweetCount;
    private Integer tweetQuoteCount;

    private boolean following;
    private boolean verified;
    private boolean locked;
    private boolean enabled;
}
