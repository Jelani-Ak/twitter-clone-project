package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public User findByUserId(ObjectId userId) throws UserIdNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("Id of " + userId + " was not found"));
    }

    public User updateUser(ObjectId userId, User user) throws UserIdNotFoundException {
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

    public User followUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = findByUserId(userId);

        existingUser.setFollowing(true);

        return userRepository.save(existingUser);
    }

    public User unfollowUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = findByUserId(userId);

        existingUser.setFollowing(false);

        return userRepository.save(existingUser);
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

    public User verifyUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = findByUserId(userId);

        existingUser.setVerified(true);

        return userRepository.save(existingUser);
    }

    public User deleteUser(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = findByUserId(userId);

        return userRepository.deleteByUserId(existingUser.getUserId());
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
        user.setUserHandleName("@" + user.getUserHandleName());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioAboutText(user.getBioAboutText());
        user.setDateOfCreation(LocalDateTime.now());
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

    public void createUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public User validateCredentials(ObjectId userId) throws UserIdNotFoundException {
        User existingUser = findByUserId(userId);

        return userRepository.findByUsernameAndPassword(existingUser.getUsername(), existingUser.getPassword());
    }

    //<editor-fold desc="Helper Functions">
    private boolean existsByUsernameAndEmail(String tempUsername, String tempEmail) {
        return userRepository.existsByUsernameAndEmail(tempUsername, tempEmail);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
    //</editor-fold>
}
