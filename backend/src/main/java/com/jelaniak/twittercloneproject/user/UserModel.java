package com.jelaniak.twittercloneproject.user;

import com.jelaniak.twittercloneproject.tweet.TweetModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String userId;
    private String displayName;
    private String userHandle;
    private String bioLocation;
    private String bioExternalLink;
    private String bioText;
    private LocalDate creationDate;
    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;
    private String follow;
    private Set<String> following;
    private Set<String> followers;
    private Set<String> followersMutual;
    private List<TweetModel> tweets;
    private Integer tweetCount;
    private Integer tweetQuoteCount;
}
