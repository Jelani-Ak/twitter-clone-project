package com.jelaniak.twittercloneproject.service;

import static com.jelaniak.twittercloneproject.utils.TweetUtility.getNewTweet;
import static com.jelaniak.twittercloneproject.utils.UserUtility.getNewUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;

@Disabled
@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    // TODO: Needs remaking entirely

    @Autowired
    private UserRepository userRepository;    
    @Mock
    private UserService userService;
    @Mock
    private TweetService tweetService;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private AdminService adminService;
    @Mock
    private UserProfileService userProfileService;


    @AfterEach
    void tearDown() {
        if (!adminService.getAllUsers().isEmpty()) {
            adminService.deleteAllUsers();
        }
    }

    @Test
    @Order(1)
    void getAllUsersAndAssertNotNullAndNotEmpty() throws Exception {
        //given - precondition or setup
        authenticationService.createUser(getNewUser(1));
        authenticationService.createUser(getNewUser(2));
        authenticationService.createUser(getNewUser(3));

        //when - action or the behaviour that we are going test
        adminService.getAllUsers();

        //then - verify the output
        assertThat(adminService.getAllUsers()).isNotNull();
        assertThat(adminService.getAllUsers().isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    void updateUserAndAssertUserInRepositoryMatches() throws UserIdNotFoundException, UserAlreadyExistsException {
        //given - precondition or setup
        User userOne = getNewUser(1);
        authenticationService.createUser(userOne);

        User userTwo = getNewUser(2);
        authenticationService.createUser(userTwo);

        //when - action or the behaviour that we are going test
        userProfileService.updateUser(userOne.getUserId(), userTwo);

        //then - verify the output
        assertThat(userService.findByUserId(userOne.getUserId()))
                .usingRecursiveComparison()
                .ignoringFields("userId", "dateOfCreation", "tweetQuoteCount")
                .isEqualTo(userService.findByUserId(userTwo.getUserId()));
    }

    @Test
    @Order(3)
    void updateUserAndThrowUserIdNotFoundException() throws UserAlreadyExistsException {
        //given - precondition or setup
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);

        authenticationService.createUser(userTwo);

        //when - action or the behaviour that we are going test
        Executable executable = () -> userProfileService.updateUser(userOne.getUserId(), userTwo);

        Exception exception = Assertions.assertThrows(UserIdNotFoundException.class, executable);

        //then - verify the output
        assertEquals(exception.getMessage(), "Id of " + userOne.getUserId() + " was not found");
    }

    @Test
    void getFollowDataAndAssertUsersYouFollowHasMoreThanZero() throws UserIdNotFoundException, UserAlreadyExistsException {
        //given - precondition or setup
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);
        User userThree = getNewUser(3);
        User userFour = getNewUser(4);
        User userFive = getNewUser(5);
        User userSix = getNewUser(6);

        Set<User> users = new HashSet<>(Set.of(
                userTwo,
                userThree,
                userFour,
                userFive,
                userSix
        ));

        userOne.getUsersYouFollow().addAll(users);

        authenticationService.createUser(userOne);

        //when - action or the behaviour that we are going test
        //then - verify the output
        assertThat(userService.findByUserId(userOne.getUserId()).getUsersYouFollow().size() > 0).isTrue();
    }

    @Test
    void getTweetDataAndAssertTweetsHasMoreThanZero() throws Exception {
        //given - precondition or setup
        User userOne = getNewUser(1);

        Tweet tweetOne = getNewTweet(1, userOne, null);
        Tweet tweetTwo = getNewTweet(2, userOne, null);
        Tweet tweetThree = getNewTweet(3, userOne, null);
        Tweet tweetFour = getNewTweet(4, userOne, null);
        Tweet tweetFive = getNewTweet(5, userOne, null);

        tweetService.createTweet(tweetOne);
        tweetService.createTweet(tweetTwo);
        tweetService.createTweet(tweetThree);
        tweetService.createTweet(tweetFour);
        tweetService.createTweet(tweetFive);

        userOne.getTweets().addAll(List.of(
                tweetService.findByTweetId(tweetOne.getTweetId()),
                tweetService.findByTweetId(tweetTwo.getTweetId()),
                tweetService.findByTweetId(tweetThree.getTweetId()),
                tweetService.findByTweetId(tweetFour.getTweetId()),
                tweetService.findByTweetId(tweetFive.getTweetId())
        ));

        authenticationService.createUser(userOne);

        //when - action or the behaviour that we are going test
        //then - verify the output
        assertThat(userService.findByUserId(userOne.getUserId()).getTweets().size() > 1).isTrue();
        assertThat(userService.findByUserId(userOne.getUserId()).getTweets().size() > 4).isTrue();
    }

    @Test
    void deleteUserAndAssertThatUsersInRepositoryIsZero() throws UserAlreadyExistsException, UserIdNotFoundException {
        //given - precondition or setup
        User user = getNewUser(1);
        authenticationService.createUser(user);

        //when - action or the behaviour that we are going test
        userProfileService.deleteUser(user.getUserId());

        //then - verify the output
        assertThat(userRepository.findAll().size() == 0).isTrue();

    }

    @Test
    void createUsersAndAssertUsersInRepoIsGreaterThanOneAndGreaterThanFour() {
        //given - precondition or setup
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);
        User userThree = getNewUser(3);
        User userFour = getNewUser(4);
        User userFive = getNewUser(5);

        //when - action or the behaviour that we are going test
        adminService.createAllUsers(List.of(
                userOne,
                userTwo,
                userThree,
                userFour,
                userFive
        ));

        //then - verify the output
        assertThat(adminService.getAllUsers().size() > 1).isTrue();
        assertThat(adminService.getAllUsers().size() > 4).isTrue();
    }

    @Test
    @Order(10)
    void findByUserIdAndAssertUserIdInRepositoryMatches() throws UserAlreadyExistsException, UserIdNotFoundException {
        //given - precondition or setup
        User user = getNewUser(1);
        authenticationService.createUser(user);

        ObjectId idToAdd = user.getUserId();

        //when - action or the behaviour that we are going test
        ObjectId idInRepo = Objects.requireNonNull(userService.findByUserId(idToAdd)).getUserId();

        //then - verify the output
        assertThat(idToAdd).isEqualTo(idInRepo);
    }
}