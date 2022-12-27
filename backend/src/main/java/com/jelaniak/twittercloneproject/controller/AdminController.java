package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(
            value = "/get-all-users",
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{userId}/delete-user",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId userId) throws UserNotFoundException {
        adminService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/get-all-tokens",
            method = RequestMethod.GET)
    public ResponseEntity<List<ConfirmationToken>> getAllConfirmationTokens() {
        return new ResponseEntity<>(adminService.getAllConfirmationTokens(), HttpStatus.OK);
    }
}
