package com.pozafly.tripllo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiRequest {

    private String id;
    private String password;
    private String email;
    private String name;

}
