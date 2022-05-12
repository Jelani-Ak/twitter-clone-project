package com.jelaniak.twittercloneproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private static UserController userController;

    @Autowired
    private static UserRepository userRepository;

    private static ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private User user, userDebug;


    @BeforeAll
    static void beforeAll() {
        mapper = new ObjectMapper();
        userRepository = mock(UserRepository.class);
        userController = new UserController(new UserService(userRepository));
    }

    @BeforeEach
    void setUp() {
        user = getUser();
        userDebug = getUserDebug();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    @Order(1)
    void createUserAndCheckStatusCodeIsCreated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void createDuplicateUserAndThrowUserAlreadyExistsException() {
        when(userRepository.existsByUsernameAndEmail(user.getUsername(), user.getEmail()))
                .thenReturn(true);

        UserAlreadyExistsException thrownMessage = assertThrows(
                UserAlreadyExistsException.class,
                () -> userController.createUser(user)
        );

        assertTrue(thrownMessage.getMessage().contains("""
                """ + "Username "
                + user.getUsername() + " and email "
                + user.getEmail() + " already exists"
        ));
    }

    private User getUser() {
        User user = new User();

        user.setUserId(new ObjectId());
        user.setUsername("UserOne");
        user.setPassword("userone");
        user.setEmail("UserOne@example.co.uk");
        user.setDateOfCreation(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setFollowing(false);
        user.setVerified(false);

        return user;
    }

    private User getUserDebug() {
        User user = new User();

        user.setUserId(new ObjectId());
        user.setUsername("UserTwo");
        user.setPassword("usertwo");
        user.setEmail("UserTwo@example.org");
        user.setDisplayName("User 2");
        user.setUserHandleName("@User-Two");
        user.setBioAboutText("UserTwo Bio About Text");
        user.setBioLocation("UserTwo Bio Location");
        user.setBioExternalLink("UserTwo Bio External Link");
        user.setDateOfCreation(LocalDateTime.of(2, 2, 2, 2, 2));
        user.setPictureAvatarUrl("https://UserTwoAvatar.org/example");
        user.setPictureBackgroundUrl("https://UserTwoBackground.org/example");
        user.setUsersYouFollow(new HashSet<>());
        user.setUsersFollowingYou(new HashSet<>());
        user.setMutualFollowers(new HashSet<>());
        user.setTweets(new ArrayList<>());
        user.setTweetCount(user.getTweets().size());
        user.setTweetQuoteCount(3);
        user.setFollowing(false);
        user.setVerified(false);

        return user;
    }
}

