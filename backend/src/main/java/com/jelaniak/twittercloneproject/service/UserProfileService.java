package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(
            UserService userService,
            UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public User deleteUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = userService.findByUserId(userId);

        return userRepository.deleteByUserId(existingUser.getUserId());
    }

    public User updateUser(ObjectId userId, User user) throws UserIdNotFoundException {
        User existingUser = userService.findByUserId(userId);

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setDisplayName(user.getDisplayName());
        existingUser.setUserHandleName(user.getUserHandleName());
        existingUser.setBioLocation(user.getBioLocation());
        existingUser.setBioExternalLink(user.getBioExternalLink());
        existingUser.setBioAboutText(user.getBioAboutText());
        existingUser.setPictureAvatarUrl(user.getPictureAvatarUrl());
        existingUser.setPictureBackgroundUrl(user.getPictureBackgroundUrl());

        return userRepository.save(existingUser);
    }
}
