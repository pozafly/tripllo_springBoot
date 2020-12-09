package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value = "V2")
public class GetController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "exam", notes = "예제입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 500, message = "Internal Server Error !!"),
            @ApiResponse(code = 404, message = "Not Found !!")
    })
    @GetMapping("/getMethod")
    public User getRequest() {
        User oneUser = userService.getUser();
        System.out.println("!!!!!!!!!!!!");
        System.out.println(oneUser);
        return oneUser;
    }
}
