package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.BadCredentialsException;
import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @RequestMapping(
            value = "/register/create",
            method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/update/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(
            @PathVariable("id") ObjectId id,
            @RequestBody User user) throws UserIdNotFoundException {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserById(@PathVariable ObjectId id) throws UserIdNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/get/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<User> findByUserId(@PathVariable ObjectId id) throws UserIdNotFoundException {
        return new ResponseEntity<>(userService.findByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/get/all",
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST)
    public ResponseEntity<User> loginUser(@RequestBody User user) throws BadCredentialsException {
        return new ResponseEntity<>(userService.loginUser(user), HttpStatus.ACCEPTED);
    }
}
