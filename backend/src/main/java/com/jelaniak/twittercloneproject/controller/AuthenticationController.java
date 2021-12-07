package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    @PostMapping("/signup")
    public void signup(@RequestBody RegisterRequest registerRequest) {

    }
}
