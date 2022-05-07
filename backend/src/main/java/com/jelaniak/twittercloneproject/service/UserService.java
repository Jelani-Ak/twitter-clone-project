package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(ObjectId userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NullPointerException("User with userId: [" + userId + "] was not found."));
    }

    public User updateUser(ObjectId id, User user) {
        User savedUser = findByUserId(id);

        savedUser.setPassword((user.getPassword()));
        savedUser.setEmail(user.getEmail());
        savedUser.setDisplayName(user.getDisplayName());
        savedUser.setUserHandle(user.getUserHandle());
        savedUser.setBioLocation(user.getBioLocation());
        savedUser.setBioExternalLink(user.getBioExternalLink());
        savedUser.setBioText(user.getBioText());
        savedUser.setPictureAvatarUrl(user.getPictureAvatarUrl());
        savedUser.setPictureBackgroundUrl(user.getPictureBackgroundUrl());

        return userRepository.save(savedUser);
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
        user.setVerified(true);
    }

    public void deleteUser(ObjectId userId) {
        userRepository.deleteByUserId(userId);
    }

    public User createUser(User user) throws Exception {
        String tempEmail = user.getEmail();
        String tempUsername = user.getUsername();

        //Check if username and email is valid
        if ((tempEmail != null && !"".equals(tempEmail)) && (tempUsername != null && !"".equals(tempUsername))) {
            //Check if username or email already exists
            if (userRepository.findByUsernameAndEmail(tempUsername, tempEmail).isPresent()) {
                throw new Exception("User with " + tempEmail + " already exists");
            }

            user.setUserId(new ObjectId());
            user.setUsername(user.getUsername());
            user.setPassword(user.getPassword());
            user.setCreatedDate(user.getCreatedDate());
            user.setFollow(false);
            user.setVerified(false);
        }

        return userRepository.save(user);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // Create a user defining all user credentials
    public User createUserDebug(User user) throws Exception {
        String tempEmail = user.getEmail();
        String tempUsername = user.getUsername();

        //Check if username and email is valid
        if ((tempEmail != null && !"".equals(tempEmail)) && (tempUsername != null && !"".equals(tempUsername))) {
            //Check if username or email already exists
            if (userRepository.findByUsernameAndEmail(tempUsername, tempEmail).isPresent()) {
                throw new Exception("User with " + tempEmail + " already exists");
            }

            user.setUserId(new ObjectId());
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
            user.setVerified(user.isVerified());
        }

        return userRepository.save(user);
    }
}
