package com.jelaniak.twittercloneproject.user;

import com.jelaniak.twittercloneproject.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUserDebug(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
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
        user.setEnabled(user.isEnabled());
        return userRepository.save(user);
    }

    public User createUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setCreatedDate(user.getCreatedDate());
        user.setFollow(user.isFollow());
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
        user.setPassword(user.getPassword());
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
