package com.jelaniak.twittercloneproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;


@Data
@NoArgsConstructor
@Table(name = "Token")
public class VerificationToken {

    @Id
    private String id;
    private String token;
    @OneToOne(fetch = LAZY)
    private User user;
    private Instant expiryDate;
}
