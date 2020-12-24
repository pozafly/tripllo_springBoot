package com.pozafly.tripllo.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginApiResponse {

    private String token;
    private String userName;

}