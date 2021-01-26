package com.pozafly.tripllo.email.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.email.model.Email;
import com.pozafly.tripllo.email.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Email V1")
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Message> checkAndSendEmail(
            @RequestBody Email email
    ) {
        return emailService.checkAndSendEmail(email);
    }

}
