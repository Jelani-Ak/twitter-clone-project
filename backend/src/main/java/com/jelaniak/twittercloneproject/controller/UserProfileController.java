package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.dto.request.UpdateUserDTO;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.service.UserProfileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/user/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @RequestMapping(
            value = "/{userId}/delete-user",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId userId) throws UserNotFoundException {
        userProfileService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/update-user",
            method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO data) throws UserNotFoundException {
        userProfileService.updateUser(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
