package com.pozafly.tripllo.email.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.email.model.Email;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    public ResponseEntity<Message> checkAndSendEmail(Email email);
}
