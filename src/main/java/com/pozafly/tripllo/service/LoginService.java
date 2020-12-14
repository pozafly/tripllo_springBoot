package com.pozafly.tripllo.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.model.request.LoginApiRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<Message> createToken(LoginApiRequest loginRequest);
}
