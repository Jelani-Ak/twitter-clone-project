package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.User;

public class UserUtility {
    /**
     * Returns a newly created user with details that are concatenated with
     * the number given. The number parameter is specified in order to
     * create a new user with unique details
     *
     * @param number identifier to give a user unique details
     * @return a new user
     */
    public static User getNewUser(int number) {
        User user = new User();

        user.setUserId(ObjectId.get());
        user.setUsername("User" + number);
        user.setPassword("password" + number);
        user.setEmail("User" + number + "@example.org");
        user.setDisplayName("User " + number);
        user.setUserHandleName("@User-" + number);
        user.setBioAboutText("User" + number + " Bio About Text");
        user.setBioLocation("User" + number + " Bio Location");
        user.setBioExternalLink("User" + number + " Bio External Link");
        user.setDateOfCreation(LocalDateTime.of(number, number, number, number, number));
        user.setPictureAvatarUrl("https://User" + number + "Avatar.org/example");
        user.setPictureBackgroundUrl("https://User" + number + "Background.org/example");
        user.setUsersYouFollow(new HashSet<>());
        user.setUsersFollowingYou(new HashSet<>());
        user.setMutualFollowers(new HashSet<>());
        user.setTweets(new HashSet<>());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(number);
        user.setFollowing(false);
        user.setVerified(false);

        return user;
    }
}
