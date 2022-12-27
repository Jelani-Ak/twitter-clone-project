package com.jelaniak.twittercloneproject.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryConfig {
    private final String API_KEY;
    private final String CLOUD_NAME;
    private final String API_SECRET_KEY;

    public CloudinaryConfig(
            @Value("${API_KEY}") String API_KEY,
            @Value("${CLOUD_NAME}") String CLOUD_NAME,
            @Value("${API_SECRET_KEY}") String API_SECRET_KEY) {
        this.API_KEY = API_KEY;
        this.CLOUD_NAME = CLOUD_NAME;
        this.API_SECRET_KEY = API_SECRET_KEY;
    }

    @PostConstruct
    public void setup() {
        double startTimer = System.nanoTime();

        log.info("Setting up Cloudinary..");

        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "api_key", this.API_KEY,
                    "cloud_name", this.CLOUD_NAME,
                    "api_secret", this.API_SECRET_KEY,
                    "secure", true
            ));

            SingletonManager manager = new SingletonManager();
            manager.setCloudinary(cloudinary);
            manager.init();

            double endTimer = System.nanoTime();
            double secondsTaken = ((endTimer - startTimer) / 1_000_000_000);
            log.info("Cloudinary setup finished in: " + secondsTaken + " seconds");
        } catch (Exception error) {
            log.warn("Failed to setup Cloudinary", error);
            throw new RuntimeException(error);
        }
    }
}
