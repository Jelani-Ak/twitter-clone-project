package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Create a new user with all credentials
    public User createUserDebug(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandle(user.getUserHandle());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioText(user.getBioText());
        user.setCreatedDate(user.getCreatedDate());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        user.setFollow(user.isFollow());
        user.setTweetCount(user.getTweetCount());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
        user.setEnabled(user.isEnabled());
        return userRepository.save(user);
    }

    // Create a new user
    public User createUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setCreatedDate(user.getCreatedDate());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id: [" + id + "] was not found."));
    }

    public User updateUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandle(user.getUserHandle());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioText(user.getBioText());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        return userRepository.save(user);
    }

    public void followUser(User user) {
        user.setFollow(user.isFollow());
        userRepository.save(user);
    }

    public void validateUser(User user) {
        user.setEnabled(user.isEnabled());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
