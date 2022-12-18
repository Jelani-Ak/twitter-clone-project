package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.SignInRequestDTO;
import com.jelaniak.twittercloneproject.dto.request.SignUpRequestDTO;
import com.jelaniak.twittercloneproject.exception.comment.ConfirmationTokenExpiredException;
import com.jelaniak.twittercloneproject.exception.comment.ConfirmationTokenNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.EmailAlreadyConfirmedException;
import com.jelaniak.twittercloneproject.exception.user.EmailNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.service.AuthenticationService;
import com.jelaniak.twittercloneproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException {
        userService.signUp(signUpRequest);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDTO signInRequest) {
        return new ResponseEntity<>(authenticationService.signIn(signInRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/sign-out", method = RequestMethod.POST)
    public ResponseEntity<?> signOut() {
        authenticationService.signOut();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) throws
            EmailAlreadyConfirmedException,
            ConfirmationTokenExpiredException,
            ConfirmationTokenNotFoundException,
            EmailNotFoundException {
        authenticationService.confirmToken(token);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/delete-token", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteToken(@RequestBody String token) throws ConfirmationTokenNotFoundException {
        authenticationService.deleteToken(token);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
