package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String displayName; // (ExampleName)
    private String userHandleName; // (@ExampleName)
    private String bioAboutText;
    private String bioLocation;
    private String bioExternalLink;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfCreation;

    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;

    private Set<User> usersYouFollow = new LinkedHashSet<>();
    private Set<User> usersFollowingYou = new LinkedHashSet<>();
    private Set<User> mutualFollowers = new LinkedHashSet<>();
    private List<Tweet> tweets = new ArrayList<>();

    private Integer tweetCount;
    private Integer tweetQuoteCount;

    private boolean following;
    private boolean verified;

    public String encrypt(String password) {
        return "";
    }
}
