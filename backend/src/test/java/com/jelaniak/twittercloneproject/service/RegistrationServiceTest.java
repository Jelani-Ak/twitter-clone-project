package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void createUser() {
        //given - precondition or setup
        User user = new User();

        user.setUsername("Jelani");
        user.setPassword("password");
        user.setEmail("JelaniTestEmail@test.co.uk");
        user.setDisplayName("Jayako");

        //when - action or the behaviour that we are going test
        userRepository.save(user);

        //then - verify the output
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }
}