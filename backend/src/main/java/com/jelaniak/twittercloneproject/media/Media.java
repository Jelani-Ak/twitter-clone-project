package com.jelaniak.twittercloneproject.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    private String mediaId;
    private String filename;
    private String mediaUrl;
}
