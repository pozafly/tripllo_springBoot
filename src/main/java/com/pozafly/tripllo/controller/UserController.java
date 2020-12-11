package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.network.Header;
import com.pozafly.tripllo.model.response.UserApiResponse;
import com.pozafly.tripllo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("{id}")
    public Header<UserApiResponse> getUser(@PathVariable String id) {

        return userService.getUser(id);
    }

}
