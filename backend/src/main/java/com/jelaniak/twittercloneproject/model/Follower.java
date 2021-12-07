package com.jelaniak.twittercloneproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id
    private String followersId;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private List<User> username;
}
