package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) throws Exception {
        return registrationService.createUser(user);
    }

    @PostMapping("/user/debug")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUserDebug(@RequestBody User user) throws Exception {
        return registrationService.createUserDebug(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) throws Exception {
        String tempUsername = user.getUsername();
        String tempPassword = user.getPassword();
        User tempUser = null;

        if (tempUsername != null && tempPassword != null) {
            tempUser = registrationService.findByUsernameAndPassword(tempUsername, tempPassword);
        }

        if (tempUser == null) {
            throw new Exception("Bad credentials");
        }

        return tempUser;
    }
}
