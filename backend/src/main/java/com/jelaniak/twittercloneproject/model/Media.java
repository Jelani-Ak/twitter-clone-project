package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "Media")
public class Media {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId mediaId;
    private String mediaUrl;
    private String mediaType;
    private String mediaKey;
}
