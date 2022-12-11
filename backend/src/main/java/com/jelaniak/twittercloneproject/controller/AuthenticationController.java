package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.LoginRequestDTO;
import com.jelaniak.twittercloneproject.dto.request.RegisterRequestDTO;
import com.jelaniak.twittercloneproject.exception.*;
import com.jelaniak.twittercloneproject.service.AuthenticationService;
import com.jelaniak.twittercloneproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(
            value = "/sign-up",
            method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody RegisterRequestDTO registerRequest) throws UserAlreadyExistsException {
        userService.register(registerRequest);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }

    @RequestMapping(
            value = "/sign-in",
            method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDTO loginRequest) throws BadCredentialsException {
        return new ResponseEntity<>(authenticationService.logUserIn(loginRequest), HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/confirm",
            method = RequestMethod.GET)
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token)
            throws EmailAlreadyConfirmedException, ConfirmationTokenExpiredException, ConfirmationTokenNotFoundException, EmailNotFoundException {
        authenticationService.confirmToken(token);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
