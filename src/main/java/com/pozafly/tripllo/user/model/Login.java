package com.pozafly.tripllo.user.model;

import lombok.Data;

@Data
public class Login {
    private String id;
    private String password;
    private String token;
    private String picture;
    private String bio;
    private String recentBoard;
    private String invitedBoard;
}
