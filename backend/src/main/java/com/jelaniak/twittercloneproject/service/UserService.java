package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.IdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(ObjectId userId) throws IdNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("Id of " + userId + " was not found"));
    }

    public User updateUser(ObjectId userId, User user) throws IdNotFoundException {
        User existingUser = findByUserId(userId);

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

    // Create a user defining all user credentials
    public User createUser(User user) throws UserAlreadyExistsException {
        String tempUsername = user.getUsername();
        String tempEmail = user.getEmail();

        //Check if username and email already exists
        if (existsByUsernameAndEmail(tempUsername, tempEmail)) {
            throw new UserAlreadyExistsException("""
                    """ + "Username " + tempUsername + " and email "
                    + tempEmail + " already exists");
        }

        user.setUserId(new ObjectId());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandleName(user.getUserHandleName());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioAboutText(user.getBioAboutText());
        user.setDateOfCreation(LocalDateTime.now());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        user.setUsersYouFollow(user.getUsersYouFollow());
        user.setUsersFollowingYou(user.getUsersFollowingYou());
        user.setMutualFollowers(user.getMutualFollowers());
//        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
        user.setFollowing(false);
        user.setVerified(false);

        return userRepository.save(user);
    }

    public void createUsers(List<User> users) {
        userRepository.saveAll(users);
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
