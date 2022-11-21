package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.*;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.AuthenticationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(authenticationService.createUser(user), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST)
    public ResponseEntity<User> logUserIn(@RequestBody User user) throws BadCredentialsException {
        return new ResponseEntity<>(authenticationService.logUserIn(user), HttpStatus.ACCEPTED);
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
