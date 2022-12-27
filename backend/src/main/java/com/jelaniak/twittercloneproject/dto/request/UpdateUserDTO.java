package com.jelaniak.twittercloneproject.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UpdateUserDTO {
    private ObjectId userId;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String userHandleName;
    private String bioAboutText;
    private String bioLocation;
    private String bioExternalLink;
}
