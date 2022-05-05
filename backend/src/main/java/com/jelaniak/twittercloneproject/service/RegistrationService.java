package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws Exception {
        String tempEmail = user.getEmail();

        //Check if email is valid
        if (tempEmail != null && !"".equals(tempEmail)) {
            //Check if email already exists
            if (userRepository.findByEmail(tempEmail) != null) {
                throw new Exception("User with " + tempEmail + " already exists");
            }

            user.setUsername(user.getUsername());
            user.setPassword(user.getPassword());
            user.setCreatedDate(user.getCreatedDate());
            user.setFollow(false);
            user.setEnabled(false);
        }

        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // Create a user defining all details
    public User createUserDebug(User user) throws Exception {
        String tempEmail = user.getEmail();

        //Check if email is valid
        if (tempEmail != null && !"".equals(tempEmail)) {
            //Check if email already exists
            if (userRepository.findByEmail(tempEmail) != null) {
                throw new Exception("User with " + tempEmail + " already exists");
            }

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
        }

        return userRepository.save(user);
    }
}
