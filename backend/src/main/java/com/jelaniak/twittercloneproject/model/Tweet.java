package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Tweet")
public class Tweet {

    @Id
    private ObjectId tweetId;
    private String tweetUrl;

    @DBRef
    private User user;

    @DBRef
    private Media media;

    @NotBlank
    @Size(max = 280)
    private String content;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @DBRef
    private List<Comment> comment = new ArrayList<>();

    private Integer commentCount;
    private Integer retweetCount;
    private Integer likeCount;

    private TweetType tweetType;
}

