package com.jelaniak.twittercloneproject.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.jelaniak.twittercloneproject.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(CloudinaryConfig.class);

    @Value("${CLOUD_NAME}")
    private String CLOUD_NAME;

    @Value("${API_KEY}")
    private String API_KEY;

    @Value("${API_SECRET_KEY}")
    private String API_SECRET_KEY;

    @PostConstruct
    public void setup() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", getCLOUD_NAME(),
                "api_key", getAPI_KEY(),
                "api_secret", getAPI_SECRET_KEY(),
                "secure", true
        ));
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();

        if (getCLOUD_NAME() != null && getAPI_KEY() != null && getAPI_SECRET_KEY() != null) {
            LOGGER.info("Cloudinary setup successful");
        } else {
            LOGGER.warn("Failed to setup Cloudinary");
        }
    }

    @Bean
    public String getCLOUD_NAME() {
        return CLOUD_NAME;
    }

    @Bean
    public String getAPI_KEY() {
        return API_KEY;
    }

    @Bean
    public String getAPI_SECRET_KEY() {
        return API_SECRET_KEY;
    }
}
