package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User deleteUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = userService.findByUserId(userId);

        return userRepository.deleteByUserId(existingUser.getUserId());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void createAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }
}
