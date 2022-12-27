package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AdminService(
            UserService userService,
            ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public void deleteUser(ObjectId userId) throws UserNotFoundException {
        User existingUser = userService.findByUserId(userId);

        userService.deleteByUserId(existingUser.getUserId());
    }

    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    public void createAllUsers(List<User> users) {
        userService.saveAllUsers(users);
    }

    public List<ConfirmationToken> getAllConfirmationTokens() {
        return confirmationTokenService.findAll();
    }
}
