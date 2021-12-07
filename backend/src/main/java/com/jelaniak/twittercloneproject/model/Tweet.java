package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    private String tweetId;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private List<User> username;

    @Nullable
    private String url;

    @Lob
    @Nullable
    private String content;

    @Nullable
    private String mediaUrl;

    private Integer commentCount;
    private Integer retweetCount;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer likeCount;
    private TweetType tweetType;
}

