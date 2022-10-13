package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserId(ObjectId userId) throws UserIdNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("Id of " + userId + " was not found"));
    }
}
