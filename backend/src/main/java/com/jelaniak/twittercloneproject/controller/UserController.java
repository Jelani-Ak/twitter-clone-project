package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/create/debug")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUserDebug(@RequestBody User user) {
        return userService.createUserDebug(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable String id) throws Exception {
        return userService.findUserById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
