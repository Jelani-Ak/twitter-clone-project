package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.IdNotFoundException;
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
    void updateUser() throws IdNotFoundException, UserAlreadyExistsException {
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
    void followUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.followUser(user);

        //then - verify the output
        assertThat(user.isFollowing()).isTrue();
    }

    @Test
    @Order(4)
    void unfollowUser() {
        //given - precondition or setup
        User user = new User();
        user.setFollowing(true);
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.unfollowUser(user);

        //then - verify the output
        assertThat(user.isFollowing()).isFalse();
    }

    @Test
    @Order(5)
    @Disabled
    void getFollowData() throws IdNotFoundException {
        //given - precondition or setup
        User user = getNewUser(1);

        userRepository.save(user);

        user.setUsersYouFollow(Set.of(
                getNewUser(2),
                getNewUser(3),
                getNewUser(4),
                getNewUser(5),
                getNewUser(6)
        ));

        //when - action or the behaviour that we are going test
        int followedUsers = userService.findByUserId(user.getUserId()).getUsersYouFollow().size();
        System.out.println(followedUsers);

        //then - verify the output
        assertThat(followedUsers > 0).isTrue();
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
    void validateUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.validateUser(user);

        //then - verify the output
        assertThat(user.isVerified()).isTrue();
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

    private User getNewUser(int number) {
        User user = new User();

        user.setUserId(new ObjectId());
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
        user.setTweets(new ArrayList<>());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(user.getTweetCount());
        user.setFollowing(false);
        user.setVerified(false);

        return user;
    }

    private Tweet getNewTweet(int number, User user, Media media) {
        Tweet tweet = new Tweet();

        tweet.setTweetId(new ObjectId());
        tweet.setTweetUrl("http://www.tweet" + number + ".co.uk/example");
        tweet.setUser(user);
        tweet.setMedia(media);
        tweet.setContent("Content " + number);
        tweet.setDateOfCreation(LocalDateTime.now());
        tweet.setComment(new ArrayList<>());
        tweet.setCommentCount(tweet.getComment().size());
        tweet.setLikeCount(tweet.getLikeCount());
        tweet.setTweetType(tweet.getTweetType());

        return tweet;
    }

    private Comment getNewComment(int number, User user, Tweet tweet, Media media) {
        Comment comment = new Comment();

        comment.setCommentId(new ObjectId());
        comment.setCommentUrl("http://www.commment" + number + ".co.uk/example");
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setMedia(media);
        comment.setContent("Content " + number);
        comment.setDateOfCreation(LocalDateTime.now());
        comment.setCommentCount(comment.getCommentCount());
        comment.setRetweetCount(comment.getRetweetCount());
        comment.setLikeCount(comment.getLikeCount());

        return comment;
    }

    private Media getNewMedia(int number) {
        Media media = new Media();

        media.setMediaId(new ObjectId());
        media.setFilename("Media " + number);
        media.setMediaUrl("http://www.media" + number + ".co.uk/example");

        return media;
    }
}