package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }

    @PostMapping("/register/debug")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUserDebug(@RequestBody User user) throws Exception {
        return userService.createUserDebug(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(
            @PathVariable("id") ObjectId id,
            @RequestBody User user) throws Exception {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable ObjectId id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable ObjectId id) throws Exception {
        return userService.findByUserId(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<User> loginUser(@RequestBody User user) throws Exception {
        String tempUsername = user.getUsername();
        String tempPassword = user.getPassword();
        Optional<User> tempUser = Optional.empty();

        if (tempUsername != null && tempPassword != null) {
            tempUser = userService.findByUsernameAndPassword(tempUsername, tempPassword);
        }

        if (tempUser.isEmpty()) {
            throw new Exception("Bad credentials");
        }

        return tempUser;
    }
}
