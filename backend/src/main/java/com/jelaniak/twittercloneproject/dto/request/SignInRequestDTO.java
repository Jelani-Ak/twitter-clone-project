package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String username;
    private String password;
}
