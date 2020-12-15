package com.pozafly.tripllo.user.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.LoginApiRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<Message> createToken(LoginApiRequest loginRequest);
}
