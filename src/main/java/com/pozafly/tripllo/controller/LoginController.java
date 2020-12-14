package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.request.LoginApiRequest;
import com.pozafly.tripllo.model.response.LoginApiResponse;
import com.pozafly.tripllo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("")
    public ResponseEntity<LoginApiResponse> login(@RequestBody LoginApiRequest request) {
        String token = loginService.createToken(request);
        return ResponseEntity.ok().body(new LoginApiResponse(token, request.getId()));
    }

}
