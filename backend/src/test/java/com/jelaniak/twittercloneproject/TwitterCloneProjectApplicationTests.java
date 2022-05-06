package com.jelaniak.twittercloneproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataMongoTest(properties = {"spring.mongodb.embedded.version=4.0.2"})
class TwitterCloneProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
