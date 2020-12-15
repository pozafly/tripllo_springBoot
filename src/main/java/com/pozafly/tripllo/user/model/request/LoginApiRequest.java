package com.pozafly.tripllo.user.model.request;

import lombok.Data;

@Data
public class LoginApiRequest {

    private String id;
    private String password;

}