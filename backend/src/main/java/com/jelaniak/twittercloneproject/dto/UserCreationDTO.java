package com.jelaniak.twittercloneproject.dto;

import lombok.Data;

@Data
public class UserCreationDTO {

    private String username;
    private String password;
    private String email;
    private String displayName;
    private String userHandleName;
}
