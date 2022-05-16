package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.Comment;
import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.model.Tweet;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.CommentRepository;
import com.jelaniak.twittercloneproject.repository.TweetRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.jelaniak.twittercloneproject.utils.CommentUtility.getNewComment;
import static com.jelaniak.twittercloneproject.utils.MediaUtility.getNewMedia;
import static com.jelaniak.twittercloneproject.utils.TweetUtility.getNewTweet;
import static com.jelaniak.twittercloneproject.utils.UserUtility.getNewUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private TweetService tweetService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        tweetService = new TweetService(tweetRepository, commentRepository);
    }

    @AfterEach
    void tearDown() {
        if (!userRepository.findAll().isEmpty()) {
            userRepository.deleteAll();
        }
    }

    @Test
    @Order(1)
    void getAllUsers() throws Exception {
        //given - precondition or setup
        userService.createUser(getNewUser(1));
        userService.createUser(getNewUser(2));
        userService.createUser(getNewUser(3));

        //when - action or the behaviour that we are going test
        userService.getAllUsers();

        //then - verify the output
        assertThat(userService.getAllUsers()).isNotNull();
        assertThat(userService.getAllUsers().isEmpty()).isFalse();

    }

    @Test
    @Order(2)
    void updateUser() throws UserIdNotFoundException, UserAlreadyExistsException {
        //given - precondition or setup
        User userOne = getNewUser(1);
        userService.createUser(userOne);

        User userTwo = getNewUser(2);
        userService.createUser(userTwo);

        //when - action or the behaviour that we are going test
        userService.updateUser(userOne.getUserId(), userTwo);

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

        userService.createUser(userTwo);

        //when - action or the behaviour that we are going test
        Executable executable = () -> userService.updateUser(userOne.getUserId(), userTwo);

        Exception exception = Assertions.assertThrows(UserIdNotFoundException.class, executable);

        //then - verify the output
        assertEquals(exception.getMessage(), "Id of " + userOne.getUserId() + " was not found");
    }

    @Test
    void followUser() throws UserIdNotFoundException, UserAlreadyExistsException {
        //given - precondition or setup
        User user = new User();
        userService.createUser(user);

        //when - action or the behaviour that we are going test
        userService.followUser(user.getUserId());

        //then - verify the output
        assertThat(userService.findByUserId(user.getUserId()).isFollowing()).isTrue();
    }

    @Test
    void unfollowUser() throws UserIdNotFoundException, UserAlreadyExistsException {
        //given - precondition or setup
        User user = new User();
        user.setFollowing(true);
        userService.createUser(user);

        //when - action or the behaviour that we are going test
        userService.unfollowUser(user.getUserId());

        //then - verify the output
        assertThat(userService.findByUserId(user.getUserId()).isFollowing()).isFalse();

    }

    @Test
    void getFollowData() throws UserIdNotFoundException, UserAlreadyExistsException {
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

        userService.createUser(userOne);

        System.out.println(userService.findByUserId(userOne.getUserId()).getUsersYouFollow());

        //when - action or the behaviour that we are going test
        //then - verify the output
        assertThat(userService.findByUserId(userOne.getUserId()).getUsersYouFollow().size() > 0).isTrue();
    }

    @Test
    void getTweetData() throws Exception {
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
                tweetService.findTweetById(tweetOne.getTweetId()),
                tweetService.findTweetById(tweetTwo.getTweetId()),
                tweetService.findTweetById(tweetThree.getTweetId()),
                tweetService.findTweetById(tweetFour.getTweetId()),
                tweetService.findTweetById(tweetFive.getTweetId())
        ));

        userService.createUser(userOne);

        //when - action or the behaviour that we are going test
        //then - verify the output
        assertThat(userService.findByUserId(userOne.getUserId()).getTweets().size() > 0).isTrue();
    }

    @Test
    void validateUser() throws UserIdNotFoundException {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.verifyUser(user.getUserId());

        //then - verify the output
        if (userRepository.findById(user.getUserId()).isPresent()) {
            assertThat(userRepository.findById(user.getUserId()).get().isVerified()).isTrue();
        }
    }

    @Test
    void deleteUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userRepository.delete(user);

        //then - verify the output
        assertThat(userRepository.findAll().size() == 0).isTrue();

    }

    @Test
    void createUser() {
        //given - precondition or setup
        User userOne = new User();
        User userTwo = new User();
        User userThree = new User();
        User userFour = new User();

        //when - action or the behaviour that we are going test
        userService.createUsers(List.of(
                userOne,
                userTwo,
                userThree,
                userFour
        ));

        //then - verify the output
        assertThat(userRepository.findAll().size() > 1).isTrue();
    }

    @Test
    @Order(10)
    void findByUserId() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        ObjectId idToAdd = user.getUserId();

        //when - action or the behaviour that we are going test
        ObjectId idInRepo = Objects.requireNonNull(userRepository.findById(idToAdd).orElse(null)).getUserId();

        //then - verify the output
        assertThat(idToAdd).isEqualTo(idInRepo);
    }
}