package com.pozafly.tripllo.user.model;

import lombok.Data;

@Data
public class Login {
    private String id;
    private String password;
    private String token;
}
