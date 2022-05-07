package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void getAllUsers() {
        //when
        userService.getAllUsers();

        //then
        assertThat(userRepository.findAll()).isNotNull();

    }

    @Test
    @Order(2)
    @Disabled
    void updateUser() throws Exception {
        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output

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
        assertThat(user.isFollow()).isTrue();
    }

    @Test
    @Order(4)
    void unfollowUser() {
        //given - precondition or setup
        User user = new User();
        user.setFollow(true);
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.unfollowUser(user);

        //then - verify the output
        assertThat(user.isFollow()).isFalse();
    }

    @Test
    @Order(5)
    void getFollowData() {
        //given - precondition or setup
        User user = new User();
        user.setFollowing(Set.of(
                new User("FollowOne"),
                new User("FollowTwo"),
                new User("FollowThree"),
                new User("FollowFour"),
                new User("FollowFive")
        ));

        userRepository.save(user);

        //when - action or the behaviour that we are going test
        Set<User> followedUsers = userService.findByUserId(user.getUserId()).getFollowing();

        //then - verify the output
        assertThat(followedUsers.isEmpty()).isFalse();
    }

    @Test
    @Disabled
    @Order(6)
    void getTweetData() {
        //TODO
        // - Create fake tweets
        // - Add them to a mock user

        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output
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
        userRepository.saveAll(List.of(userOne, userTwo, userThree, userFour));

        //then - verify the output
        assertThat(userRepository.findAll().size() > 1).isTrue();
    }

    @Test
    @Disabled
    @Order(10)
    void findByUserId() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    @Disabled
    @Order(11)
    void findByUsernameAndPassword() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output
    }
}