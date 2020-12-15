package com.pozafly.tripllo.user.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private String id;
    private String password;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}
