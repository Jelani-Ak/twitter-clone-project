package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataMongoTest(properties = {"spring.mongodb.embedded.version=4.0.2"})
class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void createUser() {
        //given
        User user = new User();

        user.setUsername("Jelani");
        user.setPassword("password");
        user.setEmail("JelaniTestEmail@test.co.uk");
        user.setDisplayName("Jayako");

        //when
        userRepository.save(user);

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }
}