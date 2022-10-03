package com.jelaniak.twittercloneproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static com.jelaniak.twittercloneproject.utils.UserUtility.getNewUser;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository = mock(UserRepository.class);

    @MockBean
    private UserService userService;

    @AfterEach
    void tearDown() {
        if (!Objects.requireNonNull(userController.getAllUsers().getBody()).isEmpty()) {
            userRepository.deleteAll();
        }
    }

    @Test
    void createUserAndCheckStatusCodeIsCreated() throws Exception {
        User userOne = getNewUser(1);

        when(userService.createUser(any(User.class)))
                .thenReturn(userOne);

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userOne))
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(userOne.getUsername()))
                .andExpect(jsonPath("$.password").value(userOne.getPassword()))
                .andDo(print())
                .andReturn();
    }


    @Test
    void updateUserAndCheckStatusCodeIsAccepted() throws Exception {
        User userOne = getNewUser(1);

        when(userService.updateUser(eq(userOne.getUserId()), any(User.class)))
                .thenReturn(userOne);

        mockMvc.perform(put("/api/v1/user/update/" + userOne.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userOne))
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(userOne.getUsername()))
                .andExpect(jsonPath("$.password").value(userOne.getPassword()))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteUserAndCheckStatusCodeIsOk() throws Exception {
        User userOne = getNewUser(1);

        when(userService.deleteUser(userOne.getUserId())).thenReturn(userOne);

        mockMvc.perform(delete("/api/v1/user/delete/{id}", userOne.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(userOne))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(userOne.getUsername()))
                .andExpect(jsonPath("$.password").value(userOne.getPassword()))
                .andDo(print());
    }

    @Test
    void getUserAndCheckStatusCodeIsOk() throws Exception {
        User userOne = getNewUser(1);

        when(userService.findByUserId(eq(userOne.getUserId())))
                .thenReturn(userOne);

        mockMvc.perform(get("/api/v1/user/get/{id}", userOne.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(userOne))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(userOne.getUsername()))
                .andExpect(jsonPath("$.password").value(userOne.getPassword()))
                .andDo(print());
    }

    @Test
    void getAllUsersAndCheckStatusCodeIsOk() throws Exception {
        User userOne = getNewUser(1);
        User userTwo = getNewUser(2);
        User userThree = getNewUser(3);
        User userFour = getNewUser(4);
        User userFive = getNewUser(5);

        List<User> users = List.of(userOne, userTwo, userThree, userFour, userFive);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/get/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(users))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$[0].username").value(userOne.getUsername()))
                .andExpect(jsonPath("$[0].password").value(userOne.getPassword()))

                .andExpect(jsonPath("$[1].userId").value(userTwo.getUserId().toString()))
                .andExpect(jsonPath("$[1].username").value(userTwo.getUsername()))
                .andExpect(jsonPath("$[1].password").value(userTwo.getPassword()))

                .andExpect(jsonPath("$[2].userId").value(userThree.getUserId().toString()))
                .andExpect(jsonPath("$[2].username").value(userThree.getUsername()))
                .andExpect(jsonPath("$[2].password").value(userThree.getPassword()))

                .andExpect(jsonPath("$[3].userId").value(userFour.getUserId().toString()))
                .andExpect(jsonPath("$[3].username").value(userFour.getUsername()))
                .andExpect(jsonPath("$[3].password").value(userFour.getPassword()))

                .andExpect(jsonPath("$[4].userId").value(userFive.getUserId().toString()))
                .andExpect(jsonPath("$[4].username").value(userFive.getUsername()))
                .andExpect(jsonPath("$[4].password").value(userFive.getPassword()))
                .andDo(print());
    }

    @Test
    void logUserInAndCheckStatusCodeIsAccepted() throws Exception {
        User userOne = getNewUser(1);

        when(userService.validateCredentials(userOne.getUserId())).thenReturn(userOne);

        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(userOne))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.userId").value(userOne.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(userOne.getUsername()))
                .andExpect(jsonPath("$.password").value(userOne.getPassword()))
                .andDo(print());
    }
}

