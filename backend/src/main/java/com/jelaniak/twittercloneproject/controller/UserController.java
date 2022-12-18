package com.jelaniak.twittercloneproject.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserService;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            value = "/{userId}/get-user",
            method = RequestMethod.GET)
    public ResponseEntity<User> findByUserId(@PathVariable ObjectId userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findByUserId(userId), HttpStatus.OK);
    }
}
