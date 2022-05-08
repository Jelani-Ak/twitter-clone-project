package com.jelaniak.twittercloneproject.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createUser() throws Exception {
        given(userService.createUser(any(User.class)))
                .willAnswer((invocationOnMock) -> invocationOnMock.getArgument(0));

        User user = new User();
        user.setUsername("userOne");
        user.setPassword("passwordOne");
        user.setEmail("userOne@test.co.uk");
        user.setDisplayName("User-1");

        this.mockMvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.username", is(user.getUsername())))
                .andExpect((ResultMatcher) jsonPath("$.password", is(user.getPassword())))
                .andExpect((ResultMatcher) jsonPath("$.email", is(user.getEmail())))
                .andExpect((ResultMatcher) jsonPath("$.displayName", is(user.getDisplayName())));
    }

    @Test
    void createUserDebug() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void loginUser() {
    }
}