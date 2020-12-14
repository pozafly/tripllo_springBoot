package com.pozafly.tripllo.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class Result {
    public static final String SUCCESS_MESSAGE = "성공";
    public static final String SERVER_ERROR_MESSAGE = "실패";

    private HttpStatus statusCode;
    private String message;
    private Object data;

    public static Result successInstance() {
        return new Result().success();
    }

    public Result success() {
        statusCode = HttpStatus.OK;
        message = SUCCESS_MESSAGE;
        return this;
    }

    public Result fail() {
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        message = SERVER_ERROR_MESSAGE;
        return this;
    }


}
