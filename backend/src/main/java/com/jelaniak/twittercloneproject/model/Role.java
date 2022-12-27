package com.jelaniak.twittercloneproject.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "Role")
public class Role {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId roleId;
    private RoleType role;
}
