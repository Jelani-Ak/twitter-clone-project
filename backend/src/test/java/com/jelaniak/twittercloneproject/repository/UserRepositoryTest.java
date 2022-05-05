package com.jelaniak.twittercloneproject.repository;

import com.jelaniak.twittercloneproject.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    @Test
    void findByUsername() {
        //given
        String username = "Jelani";

        User user = new User();
        user.setUsername(username);

        userRepositoryTest.save(user);

        //when
        boolean expected = userRepositoryTest.existsByUsername(username);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByUsernameAndPassword() {
    }
}