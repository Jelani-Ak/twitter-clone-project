package com.jelaniak.twittercloneproject.dto;

import lombok.Data;

@Data
public class UserProfileUpdateDTO {

    private String displayName; // (ExampleName)
    private String userHandleName; // (@ExampleName)
    private String bioAboutText;
    private String bioLocation;
    private String bioExternalLink;
    private String pictureAvatarUrl;
    private String pictureBackgroundUrl;
}
