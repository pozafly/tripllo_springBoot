package com.pozafly.tripllo.model.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Message {

    @ApiModelProperty(example = "statusCode=200")
    private StatusEnum status;
    @ApiModelProperty(example = "성공!")
    private String message;
    @ApiModelProperty(example = "{value: true, anotherValue: false}")
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
