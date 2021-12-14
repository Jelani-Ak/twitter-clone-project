package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createUserDebug(User user) {
        user.setUserId(Long.parseLong(UUID.randomUUID().toString()));
        user.setUsername(user.getUsername());
        user.setPassword((user.getPassword()));
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
        user.setFollowing(user.getFollowing());
        user.setFollowers(user.getFollowers());
        user.setFollowersMutual(user.getFollowersMutual());
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweetCount());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
        user.setFollow(user.isFollow());
        user.setEnabled(user.isEnabled());
        return userRepository.save(user);
    }

    public User createUser(User user) {
        user.setUserId(Long.parseLong(UUID.randomUUID().toString()));
        user.setUsername(user.getUsername());
        user.setPassword((user.getPassword()));
        user.setCreatedDate(user.getCreatedDate());
        user.setFollow(false);
        user.setEnabled(false);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(String id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User by id: [" + id + "] was not found."));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByUsername(email);
    }


    public User updateUser(User user) {
        user.setPassword((user.getPassword()));
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

    public void getFollowData(User user) {
        user.setFollowing(user.getFollowing());
        user.setFollowers(user.getFollowers());
        user.setFollowersMutual(user.getFollowersMutual());
    }

    public void getTweetData(User user) {
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweetCount());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
    }

    public void validateUser(User user) {
        user.setEnabled(user.isEnabled());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
