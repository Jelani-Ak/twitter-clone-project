package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Comment")
public class Comment {

    @Id
    private ObjectId commentId;
    private String commentUrl;

    @DBRef
    private User user;

    @DBRef
    private Tweet tweet;

    @DBRef
    private Media media;

    @NotBlank
    @Size(max = 280)
    private String content;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer commentCount;
    private Integer retweetCount;
    private Integer likeCount;
}
