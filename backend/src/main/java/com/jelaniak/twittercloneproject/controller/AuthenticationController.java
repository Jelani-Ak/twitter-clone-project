package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.RegisterRequest;
import com.jelaniak.twittercloneproject.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(value = HttpStatus.OK, reason = "User Registration Successful")
    public void signUp(@RequestBody RegisterRequest registerRequest) {
        authenticationService.signUp(registerRequest);

    }
}
