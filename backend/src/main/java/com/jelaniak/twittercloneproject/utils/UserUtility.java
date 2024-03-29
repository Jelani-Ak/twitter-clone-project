package com.jelaniak.twittercloneproject.utils;

import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashSet;

public class UserUtility {
    /**
     * For unit testing purposes.
     * Returns a newly created user with details that are concatenated with
     * the number given. The number parameter is specified in order to
     * create a new user with unique details
     *
     * @param number identifier to give a user unique details
     * @return a new user
     */
    public static User buildNewUser(int number) {
        User user = new User();

        user.setUserId(new ObjectId());
        user.setUsername("User" + number);
        user.setPassword("password" + number);
        user.setEmail("User" + number + "@example.org");
        user.setDisplayName("User " + number);
        user.setUserHandleName("@User-" + number);
        user.setBioAboutText("User" + number + " Bio About Text");
        user.setBioLocation("User" + number + " Bio Location");
        user.setRoles(new HashSet<>());
        user.setBioExternalLink("User" + number + " Bio External Link");
        user.setDateOfCreation(LocalDateTime.now());
        user.setPictureAvatarUrl("https://User" + number + "Avatar.org/example");
        user.setPictureBackgroundUrl("https://User" + number + "Background.org/example");
        user.setUsersYouFollow(new HashSet<>());
        user.setUsersFollowingYou(new HashSet<>());
        user.setMutualFollowers(new HashSet<>());
        user.setTweets(new HashSet<>());
        user.setLikedTweets(new HashSet<>());
        user.setComments(new HashSet<>());
        user.setLikedComments(new HashSet<>());
        user.setUsersYouFollowCount(user.getUsersYouFollow().size());
        user.setUsersFollowingYouCount(user.getUsersFollowingYou().size());
        user.setMutualFollowersCount(user.getMutualFollowers().size());
        user.setTweetCount(user.getTweets().size());
        user.setCommentCount(user.getComments().size());
        user.setFollowing(false);
        user.setVerified(false);
        user.setLocked(false);
        user.setEnabled(false);

        return user;
    }
}
