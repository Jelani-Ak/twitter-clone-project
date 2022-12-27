package com.jelaniak.twittercloneproject.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class JwtResponseDTO {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String email;
    private String token;
    private List<String> roles;
    private String username;
    private String type = "Bearer";

    public JwtResponseDTO(
            String id,
            String email,
            String username,
            String token,
            List<String> roles) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
