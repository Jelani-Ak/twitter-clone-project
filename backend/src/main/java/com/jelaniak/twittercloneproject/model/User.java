package com.jelaniak.twittercloneproject.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Document(value = "User")
public class User implements UserDetails {

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

    private UserRole userRole;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfCreation;

    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;

    private Set<User> usersYouFollow; //TODO: Create new DTO, 'FollowedUsers'
    private Set<User> usersFollowingYou; // TODO: Create new DTO, 'UsersFollowing'
    private Set<User> mutualFollowers; // TODO: Create new DTO, 'MutualFollowers'
    private Set<Tweet> tweets;

    private Integer tweetCount;
    private Integer tweetQuoteCount;

    private boolean following;
    private boolean verified;
    private boolean locked;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
