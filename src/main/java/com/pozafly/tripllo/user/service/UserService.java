package com.pozafly.tripllo.user.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<Message> readUser(String id);
    public ResponseEntity<Message> rtnIdValid(String id);
    public ResponseEntity<Message> createUser(UserApiRequest request);
    public ResponseEntity<Message> updateUser(UserApiRequest request);
    public ResponseEntity<Message> deleteUser(String id);
}
