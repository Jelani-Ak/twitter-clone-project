package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.UpdateUserDTO;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserProfileService(
            UserService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void deleteUser(ObjectId userId) throws UserNotFoundException {
        User existingUser = userService.findByUserId(userId);

        userService.deleteByUserId(existingUser.getUserId());
    }

    public void updateUser(UpdateUserDTO data) throws UserNotFoundException {
        User existingUser = userService.findByUserId(data.getUserId());

        existingUser.setUsername(data.getUsername());

        final boolean passwordChanged = !data.getPassword().equals("");
        if (passwordChanged) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
        }

        existingUser.setEmail(data.getEmail());
        existingUser.setDisplayName(data.getDisplayName());
        existingUser.setUserHandleName(data.getUserHandleName());
        existingUser.setBioLocation(data.getBioLocation());
        existingUser.setBioExternalLink(data.getBioExternalLink());
        existingUser.setBioAboutText(data.getBioAboutText());
//        existingUser.setPictureAvatarUrl(data.getPictureAvatarUrl());
//        existingUser.setPictureBackgroundUrl(data.getPictureBackgroundUrl());

        userService.saveUser(existingUser);
    }
}
