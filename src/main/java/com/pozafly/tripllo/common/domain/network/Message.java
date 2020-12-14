package com.pozafly.tripllo.common.domain.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class Message {

    @ApiModelProperty(example = "00시 00분 00초...")
    private LocalDateTime transactionTime;
    @ApiModelProperty(example = "statusCode=200")
    private StatusEnum status;
    @ApiModelProperty(example = "성공!")
    private String message;
    @ApiModelProperty(example = "{value: true, anotherValue: false}")
    private Object data;

    public Message() {
        this.transactionTime = LocalDateTime.now();
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}