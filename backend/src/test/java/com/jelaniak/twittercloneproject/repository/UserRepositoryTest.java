package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
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
        assertThat(expected).isTrue();
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
        assertThat(expected).isTrue();
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