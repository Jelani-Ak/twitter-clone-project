package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.exception.IdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return userService.createUser(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(
            @PathVariable("id") ObjectId id,
            @RequestBody User user) throws IdNotFoundException {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUserById(@PathVariable ObjectId id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findByUserId(@PathVariable ObjectId id) throws IdNotFoundException {
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
            tempUser = userService.validateCredentials(tempUsername, tempPassword);
        }

        if (tempUser.isEmpty()) {
            throw new Exception("Bad credentials");
        }

        return tempUser;
    }
}
