package com.jelaniak.twittercloneproject.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jelaniak.twittercloneproject.model.User;

@Disabled
@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

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

    @Test
    void checkUsernameAndEmailExists() {
        //given - precondition or setup
        String username = "Jelani";
        String email = "Jelani@example.co.uk";

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        userRepository.save(user);

        //when - action or the behaviour that we are going test
        boolean expected = userRepository.existsByUsernameAndEmail(username, email);

        //then - verify the output
        assertThat(expected).isTrue();
    }

    @Test
    void checkUsernameAndEmailDoesNotExist() {
        //given - precondition or setup
        String username = "Jelani";
        String email = "Jelani@example.co.uk";

        //when - action or the behaviour that we are going test
        boolean expected = userRepository.existsByUsernameAndEmail(username, email);

        //then - verify the output
        assertThat(expected).isFalse();
    }

    @Test
    void findByUsernameAndEmail() {
        //given - precondition or setup
        User user = new User();

        user.setUsername("Jelani");
        user.setEmail("Jelani@example.co.uk");

        userRepository.save(user);

        //when - action or the behavior that we are going test
        User retrievedUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());

        //then - verify the output
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("dateOfCreation")
                .isEqualTo(retrievedUser);
    }
}