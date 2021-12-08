package com.jelaniak.twittercloneproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Media")
public class Media {

    @Id
    private String mediaId;
    private String filename;
    private String mediaUrl;
}
