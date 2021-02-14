package com.pozafly.tripllo.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginApiResponse {

    private String token;
    private String id;
    private String email;
    private String name;
    private String picture;
    private String social;
    private String bio;
    private String recentBoard;
    private String invitedBoard;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}
