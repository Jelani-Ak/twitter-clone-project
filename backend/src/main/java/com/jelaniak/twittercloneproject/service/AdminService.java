package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.ConfirmationTokenRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public AdminService(
            UserService userService,
            UserRepository userRepository,
            ConfirmationTokenRepository confirmationTokenRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public User deleteUser(ObjectId userId) throws UserNotFoundException {
        User existingUser = userService.findByUserId(userId);

        return userRepository.deleteByUserId(existingUser.getUserId());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void createAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<ConfirmationToken> getAllConfirmationTokens() {
        return confirmationTokenRepository.findAll();
    }
}
