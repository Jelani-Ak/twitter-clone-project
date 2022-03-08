package com.jelaniak.twittercloneproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.core.KafkaTemplate;

@EnableMongoAuditing
@SpringBootApplication
public class TwitterCloneProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterCloneProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
            kafkaTemplate.send("TwitterClone", "Hello Kafka");
        };
    }
}
