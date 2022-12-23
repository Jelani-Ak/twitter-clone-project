package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequestDTO {
    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
