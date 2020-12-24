package com.pozafly.tripllo.user.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserApiRequest {

    @ApiModelProperty(example = "pain103")
    private String id;
    @ApiModelProperty(example = "1234")
    private String password;
    @ApiModelProperty(example = "b@b.com")
    private String email;
    @ApiModelProperty(example = "황선")
    private String name;
    @ApiModelProperty(example = "aaa.jpg")
    private String picture;
    @ApiModelProperty(example = "Google")
    private String socialYn;

}
