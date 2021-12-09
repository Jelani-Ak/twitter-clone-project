package com.jelaniak.twittercloneproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Verification")
public class VerificationToken {

    @Id
    private String tokenId;
    private String token;
    @DBRef
    private User user;
    private Instant expiryDate;
}
