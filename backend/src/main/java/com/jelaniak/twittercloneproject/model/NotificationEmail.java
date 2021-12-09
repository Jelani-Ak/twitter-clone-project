package com.jelaniak.twittercloneproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}
