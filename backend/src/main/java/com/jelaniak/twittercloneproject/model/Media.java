package com.jelaniak.twittercloneproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    private String mediaId;

    private String filename;
    private String mediaUrl;
}
