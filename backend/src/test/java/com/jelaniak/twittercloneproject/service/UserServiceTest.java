package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataMongoTest(properties = {"spring.mongodb.embedded.version=4.0.2"})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllUsers() {
        //when
        userService.getAllUsers();

        //then
        verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void updateUser() {
        //given - precondition or setup
        User userOne = new User();
        userOne.setUserId(new ObjectId());
        userOne.setUsername("userOne");
        userOne.setEmail("userOne@test.co.uk");
        userOne.setDisplayName("user1");

        User userTwo = new User();
        userTwo.setUserId(new ObjectId());
        userTwo.setUsername("userTwo");
        userTwo.setEmail("userTwo@test.co.uk");
        userTwo.setDisplayName("user2");

        userRepository.save(userOne);
        userRepository.save(userTwo);

        //when - action or the behaviour that we are going test
        userService.updateUser(userOne.getUserId(), userTwo);

        //then - verify the output
        verify(userOne).equals(userTwo);
    }

    @Test
    @Disabled
    void followUser() {
    }

    @Test
    @Disabled
    void getFollowData() {
    }

    @Test
    @Disabled
    void getTweetData() {
    }

    @Test
    @Disabled
    void validateUser() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}