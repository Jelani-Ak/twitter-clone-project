package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws Exception {
        String tempEmail = user.getEmail();

        //Check if email is valid
        if (tempEmail != null && !"".equals(tempEmail)) {
            //Check if email already exists
            if (userRepository.findByEmail(tempEmail).isPresent()) {
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

        //Check if email is valid
        if (tempEmail != null && !"".equals(tempEmail)) {
            //Check if email already exists
            if (userRepository.findByEmail(tempEmail).isPresent()) {
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
