package com.jelaniak.twittercloneproject.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Media")
public class Media {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId mediaId;
    private String filename;
    private String mediaUrl;
}
