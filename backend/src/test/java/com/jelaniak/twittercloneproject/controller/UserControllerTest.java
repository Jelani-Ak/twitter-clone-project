package com.jelaniak.twittercloneproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelaniak.twittercloneproject.exception.IdNotFoundException;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper;
    private static UserController userController;
    private static UserRepository userRepository;

    @MockBean
    private UserService userService;


    @BeforeAll
    static void beforeAll() {
        mapper = new ObjectMapper();
        userRepository = mock(UserRepository.class);
        userController = new UserController(new UserService(userRepository));
    }

    @AfterEach
    void tearDown() {
        if (!userController.getAllUsers().isEmpty()) {
            userRepository.deleteAll();
        }
    }


    @Test
    @Order(1)
    void createUserAndCheckStatusCodeIsCreated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new User()));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void createDuplicateUserAndThrowUserAlreadyExistsException() {
        User userOne = getNewUser(1);

        when(userRepository.existsByUsernameAndEmail(userOne.getUsername(), userOne.getEmail()))
                .thenReturn(true);

        UserAlreadyExistsException thrownMessage = assertThrows(
                UserAlreadyExistsException.class,
                () -> userController.createUser(userOne)
        );

        assertTrue(thrownMessage.getMessage().contains("""
                """ + "Username "
                + userOne.getUsername() + " and email "
                + userOne.getEmail() + " already exists"
        ));
    }

    @Test
    @Order(3)
    @Disabled
    void createUserAndVerifySaveToDatabase() throws UserAlreadyExistsException {
        User userThree = getNewUser(3);

        assertEquals(userThree, userController.createUser(userThree));
    }

    @Test
    @Order(4)
    @Disabled
    void updateUserAndCheckUpdatedDetailsMatch() throws Exception {
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);

        userController.createUser(userTwo);

        userController.updateUser(userOne.getUserId(), userTwo);

        assertThat(userController.findByUserId(userOne.getUserId()))
                .usingRecursiveComparison()
                .ignoringFields("userId")
                .isEqualTo(userController.findByUserId(userTwo.getUserId()));
    }


    @Test
    @Order(5)
    void updateUserAndCheckStatusCodeIsAccepted() throws Exception {
        User userOne = getNewUser(1);

        mockMvc.perform(put("/api/v1/user/update/" + userOne.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userOne))
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(6)
    void updateUserAndThrowIdNotFoundException() {
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);

        when(userRepository.existsById(userOne.getUserId()))
                .thenReturn(true);

        IdNotFoundException thrownMessage = assertThrows(
                IdNotFoundException.class,
                () -> userController.updateUser(userOne.getUserId(), userTwo)
        );

        assertTrue(thrownMessage.getMessage().contains("""
                """ + "Id of " + userOne.getUserId() + " was not found"
        ));
    }

    @Test
    @Order(7)
    @Disabled
    void deleteUser() throws Exception {
        User userThree = getNewUser(3);
        doNothing().when(userController).deleteUserById(userThree.getUserId());
        mockMvc.perform(delete("/api/v1/user/delete/{id}", userThree.getUserId()))
                .andExpect(status().isAccepted())
                .andDo(print());
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
        user.setTweetQuoteCount(number);
        user.setFollowing(false);
        user.setVerified(false);

        return user;
    }
}

