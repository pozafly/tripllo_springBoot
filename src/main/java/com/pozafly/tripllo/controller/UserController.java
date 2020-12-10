package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUser")
    public User getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

}
