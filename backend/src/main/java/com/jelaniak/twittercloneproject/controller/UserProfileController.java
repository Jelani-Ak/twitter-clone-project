package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserProfileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user/profile")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserById(@PathVariable ObjectId id) throws UserNotFoundException {
        return new ResponseEntity<>(userProfileService.deleteUser(id), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/update/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(
            @PathVariable("id") ObjectId id,
            @RequestBody User user) throws UserNotFoundException {
        return new ResponseEntity<>(userProfileService.updateUser(id, user), HttpStatus.ACCEPTED);
    }
}
