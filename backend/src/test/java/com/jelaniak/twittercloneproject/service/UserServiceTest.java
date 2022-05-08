package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void getAllUsers() throws Exception {
        //given - precondition or setup
        userService.createUser(new User());

        //when - action or the behaviour that we are going test
        userService.getAllUsers();

        //then - verify the output
        assertThat(userRepository.findAll()).isNotNull();
        assertThat(userRepository.findAll().isEmpty()).isFalse();

    }

    @Test
    @Order(2)
    void updateUser() throws Exception {
        //given - precondition or setup
        User userOne = new User();
        userOne.setUserId(new ObjectId());
        userOne.setPassword("passwordOne");
        userOne.setEmail("userOne@test.co.uk");
        userOne.setDisplayName("User-1");
        userOne.setUserHandle("@UserOne");
        userOne.setBioLocation("North");
        userOne.setBioExternalLink("https://www.user-one.co.uk");
        userOne.setBioText("Howdy, I'm userOne");
        userOne.setPictureAvatarUrl("https://www.fake.co.uk/userOne-avatar.jpg");
        userOne.setPictureBackgroundUrl("https://www.fake.co.uk/userOne-background.jpg");
        userRepository.save(userOne);

        User userTwo = new User();
        userTwo.setUserId(new ObjectId());
        userTwo.setPassword("passwordTwo");
        userTwo.setEmail("userTwo@test.co.uk");
        userTwo.setDisplayName("User-2");
        userTwo.setUserHandle("@userTwo");
        userTwo.setBioLocation("South");
        userTwo.setBioExternalLink("https://www.user-two.co.uk");
        userTwo.setBioText("Howdy, I'm userTwo");
        userTwo.setPictureAvatarUrl("https://www.fake.co.uk/userTwo-avatar.jpg");
        userTwo.setPictureBackgroundUrl("https://www.fake.co.uk/userTwo-background.jpg");
        userRepository.save(userTwo);

        //when - action or the behaviour that we are going test
        userService.updateUser(userOne.getUserId(), userTwo);

        //then - verify the output
        assertThat(userRepository.findByUserId(userOne.getUserId()).orElse(null))
                .usingRecursiveComparison()
                .ignoringFields("userId")
                .isEqualTo(userRepository.findByUserId(userTwo.getUserId()).orElse(null));
    }

    @Test
    @Order(3)
    void followUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.followUser(user);

        //then - verify the output
        assertThat(user.isFollow()).isTrue();
    }

    @Test
    @Order(4)
    void unfollowUser() {
        //given - precondition or setup
        User user = new User();
        user.setFollow(true);
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.unfollowUser(user);

        //then - verify the output
        assertThat(user.isFollow()).isFalse();
    }

    @Test
    @Order(5)
    void getFollowData() {
        //given - precondition or setup
        User user = new User();
        user.setFollowing(Set.of(
                new User(),
                new User(),
                new User(),
                new User(),
                new User()
        ));

        userRepository.save(user);

        //when - action or the behaviour that we are going test
        Set<User> followedUsers = userService.findByUserId(user.getUserId()).getFollowing();

        //then - verify the output
        assertThat(followedUsers.isEmpty()).isFalse();
    }

    @Test
    @Disabled
    @Order(6)
    void getTweetData() {
        //TODO
        // - Create fake tweets
        // - Add them to a mock user
        // - Assert that users set of tweets is populated

        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    @Order(7)
    void validateUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userService.validateUser(user);

        //then - verify the output
        assertThat(user.isVerified()).isTrue();
    }

    @Test
    @Order(8)
    void deleteUser() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        //when - action or the behaviour that we are going test
        userRepository.delete(user);

        //then - verify the output
        assertThat(userRepository.findAll().size() == 0).isTrue();

    }

    @Test
    @Order(9)
    void createUser() {
        //given - precondition or setup
        User userOne = new User();
        User userTwo = new User();
        User userThree = new User();
        User userFour = new User();

        //when - action or the behaviour that we are going test
        userRepository.saveAll(List.of(userOne, userTwo, userThree, userFour));

        //then - verify the output
        assertThat(userRepository.findAll().size() > 1).isTrue();
    }

    @Test
    @Order(10)
    void findByUserId() {
        //given - precondition or setup
        User user = new User();
        userRepository.save(user);

        ObjectId idToAdd = user.getUserId();

        //when - action or the behaviour that we are going test
        ObjectId idInRepo = Objects.requireNonNull(userRepository.findByUserId(idToAdd).orElse(null)).getUserId();

        //then - verify the output
        assertThat(idToAdd).isEqualTo(idInRepo);
    }
}