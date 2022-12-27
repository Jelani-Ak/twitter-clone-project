package com.jelaniak.twittercloneproject.dto.request;

import com.jelaniak.twittercloneproject.model.Tweet;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateTweetDTO {
    private Tweet tweet;
    private MultipartFile file;
}
