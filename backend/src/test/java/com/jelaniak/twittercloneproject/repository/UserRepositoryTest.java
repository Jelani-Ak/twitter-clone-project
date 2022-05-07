package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void checkUsernameExists() {
        //given - precondition or setup
        String username = "Jelani";

        User user = new User();
        user.setUsername(username);

        userRepository.save(user);

        //when - action or the behaviour that we are going test
        boolean expected = userRepository.existsByUsername(username);

        //then - verify the output
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void checkUsernameDoesNotExist() {
        //given
        String username = "Jelani";

        //when
        boolean expected = userRepository.existsByUsername(username);

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void checkEmailExists() {
        //given
        String email = "JelaniTestEmail@test.co.uk";

        User user = new User();
        user.setEmail(email);

        //when
        userRepository.save(user);

        boolean expected = userRepository.existsByEmail(email);

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void checkEmailDoesNotExist() {
        //given
        String email = "TestEmail@test.co.uk";

        //when
        boolean expected = userRepository.existsByEmail(email);

        //then
        assertThat(expected).isFalse();
    }
}