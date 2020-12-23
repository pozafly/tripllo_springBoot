package com.pozafly.tripllo.user.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginApiRequest {

    @ApiModelProperty(example = "pain103")
    private String id;
    @ApiModelProperty(example = "1234")
    private String password;

}