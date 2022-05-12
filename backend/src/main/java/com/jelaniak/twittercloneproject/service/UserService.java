package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return userRepository.findByUserId(userId).orElseThrow();
    }

    public User updateUser(ObjectId id, User user) {
        User savedUser = findByUserId(id);

        savedUser.setPassword(user.getPassword());
        savedUser.setEmail(user.getEmail());
        savedUser.setDisplayName(user.getDisplayName());
        savedUser.setUserHandleName(user.getUserHandleName());
        savedUser.setBioLocation(user.getBioLocation());
        savedUser.setBioExternalLink(user.getBioExternalLink());
        savedUser.setBioAboutText(user.getBioAboutText());
        savedUser.setPictureAvatarUrl(user.getPictureAvatarUrl());
        savedUser.setPictureBackgroundUrl(user.getPictureBackgroundUrl());

        return userRepository.save(savedUser);
    }

    public void followUser(User user) {
        user.setFollowing(true);
    }

    public void unfollowUser(User user) {
        user.setFollowing(false);
    }

    public void getFollowData(User user) {
        user.setUsersYouFollow(user.getUsersYouFollow());
        user.setUsersFollowingYou(user.getUsersFollowingYou());
        user.setMutualFollowers(user.getMutualFollowers());
    }

    public void getTweetData(User user) {
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
    }

    public void validateUser(User user) {
        user.setVerified(true);
    }

    public void deleteUser(ObjectId userId) {
        userRepository.deleteByUserId(userId);
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        String tempUsername = user.getUsername();
        String tempEmail = user.getEmail();

        //Check if username and email already exists
        if (existsByUsernameAndEmail(tempUsername, tempEmail)) {
            throw new UserAlreadyExistsException(
                    "Username " + tempUsername + " and email "
                            + tempEmail + " already exists");
        }

        ObjectId objectId = ObjectId.get();
        String objectIdStringValue = objectId.toHexString();
        ObjectId restoredObjectId = new ObjectId(objectIdStringValue);

        LocalDateTime timestamp = LocalDateTime.now();

        user.setUserId(restoredObjectId);
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDateOfCreation(timestamp);
        user.setFollowing(false);
        user.setVerified(false);

        return userRepository.save(user);
    }

    // Create a user defining all user credentials
    public User createUserDebug(User user) throws UserAlreadyExistsException {
        String tempUsername = user.getUsername();
        String tempEmail = user.getEmail();

        //Check if username and email already exists
        if (existsByUsernameAndEmail(tempUsername, tempEmail)) {
            throw new UserAlreadyExistsException(
                    "Username " + tempUsername + " and email "
                            + tempEmail + " already exists");
        }

        ObjectId objectId = ObjectId.get();
        String objectIdStringValue = objectId.toHexString();
        ObjectId restoredObjectId = new ObjectId(objectIdStringValue);

        LocalDateTime timestamp = LocalDateTime.now();

        user.setUserId(restoredObjectId);
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandleName(user.getUserHandleName());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioAboutText(user.getBioAboutText());
        user.setDateOfCreation(timestamp);
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        user.setUsersYouFollow(user.getUsersYouFollow());
        user.setUsersFollowingYou(user.getUsersFollowingYou());
        user.setMutualFollowers(user.getMutualFollowers());
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
        user.setFollowing(false);
        user.setVerified(false);

        return userRepository.save(user);
    }

    public Optional<User> validateCredentials(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    //<editor-fold desc="Helper Functions">
    private boolean existsByUsernameAndEmail(String tempUsername, String tempEmail) {
        return userRepository.existsByUsernameAndEmail(tempUsername, tempEmail);
    }
    //</editor-fold>
}
