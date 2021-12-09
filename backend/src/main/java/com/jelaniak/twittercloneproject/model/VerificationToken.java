package com.jelaniak.twittercloneproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;

@Data
@NoArgsConstructor
public class VerificationToken {

    @Id
    private String tokenId;
    private String token;
    @DBRef
    private User user;
    private Instant expiryDate;
}
