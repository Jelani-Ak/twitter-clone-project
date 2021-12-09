package com.jelaniak.twittercloneproject.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationBody {
    private String email;
    private String password;
}
