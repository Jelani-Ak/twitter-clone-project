package com.jelaniak.twittercloneproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    private String likeId;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tweetId", referencedColumnName = "tweetId")
    private Tweet tweet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
