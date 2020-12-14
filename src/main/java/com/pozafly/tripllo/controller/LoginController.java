package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.request.LoginApiRequest;
import com.pozafly.tripllo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;



}
