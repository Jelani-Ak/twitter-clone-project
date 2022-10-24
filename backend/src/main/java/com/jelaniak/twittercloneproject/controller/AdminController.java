package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(
            value = "/get/all/users",
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserById(@PathVariable ObjectId id) throws UserIdNotFoundException {
        return new ResponseEntity<>(adminService.deleteUser(id), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/get/all/confirmationToken",
            method = RequestMethod.GET)
    public ResponseEntity<List<ConfirmationToken>> getAllConfirmationTokens() {
        return new ResponseEntity<>(adminService.getAllConfirmationTokens(), HttpStatus.OK);
    }
}
