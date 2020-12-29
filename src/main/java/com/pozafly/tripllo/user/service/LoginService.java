package com.pozafly.tripllo.user.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.LoginApiRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {
    public ResponseEntity<Message> login(Map<String, String> userInfo);
    public ResponseEntity<Message> socialLogin(String userId);
}
