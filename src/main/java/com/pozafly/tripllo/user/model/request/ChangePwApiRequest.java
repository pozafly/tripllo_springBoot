package com.pozafly.tripllo.user.model.request;

import lombok.Data;

@Data
public class ChangePwApiRequest {

    private String currentPw;
    private String newPw;

}
