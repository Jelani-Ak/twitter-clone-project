package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static com.jelaniak.twittercloneproject.utility.CommentUtility.getNewComment;
import static com.jelaniak.twittercloneproject.utility.MediaUtility.getNewMedia;
import static com.jelaniak.twittercloneproject.utility.TweetUtility.getNewTweet;
import static com.jelaniak.twittercloneproject.utility.UserUtility.getNewUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(userRepository.findAll()).isNotNull();
        assertThat(userRepository.findAll().isEmpty()).isFalse();

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
                .ignoringFields("userId", "dateOfCreation")
                .isEqualTo(userService.findByUserId(userTwo.getUserId()));
    }

    @Test
    @Order(3)
    void followUser() throws UserIdNotFoundException {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.followUser(user.getUserId());

        //then - verify the output
        assertThat(userService.findByUserId(user.getUserId()).isFollowing()).isTrue();
    }

    @Test
    @Order(4)
    void unfollowUser() throws UserIdNotFoundException {
        //given - precondition or setup
        User user = new User();
        user.setFollowing(true);
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.unfollowUser(user.getUserId());

        //then - verify the output
        assertThat(userService.findByUserId(user.getUserId()).isFollowing()).isFalse();

    }

    @Test
    @Order(5)
    @Disabled
    void getFollowData() throws UserIdNotFoundException {
        //given - precondition or setup
        User user = getNewUser(1);

        user.setUsersYouFollow(new HashSet<>(Set.of(
                getNewUser(2),
                getNewUser(3),
                getNewUser(4),
                getNewUser(5),
                getNewUser(6)
        )));

        userRepository.save(user);

        //when - action or the behaviour that we are going test
        //then - verify the output
        assertThat(userService.findByUserId(user.getUserId()).getUsersYouFollow().size() > 0).isTrue();
    }

    @Test
    @Order(6)
    @Disabled
    void getTweetData() throws Exception {
        //given - precondition or setup
        User user = getNewUser(1);
        userService.createUser(user);

        Media media = getNewMedia(1);

        Tweet tweet = getNewTweet(1, user, media);
        tweetService.createTweet(tweet);

        Comment comment = getNewComment(1, user, tweet, media);
        tweetService.createComment(tweet.getTweetId(), comment);

        user.setTweets(List.of(
                tweetService.findTweetById(tweet.getTweetId())
        ));

        tweet.setComment(List.of(
                tweetService.findCommentById(comment.getCommentId())
        ));

        //when - action or the behaviour that we are going test
        List<Tweet> tweets = userService.findByUserId(user.getUserId()).getTweets();
        System.out.println(tweets);

        //then - verify the output
        assertThat(tweets.size() > 0).isTrue();
    }

    @Test
    @Order(7)
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
    @Order(8)
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
    @Order(9)
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