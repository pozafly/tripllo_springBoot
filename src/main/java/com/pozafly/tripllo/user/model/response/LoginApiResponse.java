package com.pozafly.tripllo.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginApiResponse {

    private String token;
    private String id;
    private String email;
    private String name;
    private String picture;
    private String bio;
    private String recent;
    private String favorite;

}
