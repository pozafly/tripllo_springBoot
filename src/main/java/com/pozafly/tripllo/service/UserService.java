package com.pozafly.tripllo.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.model.request.UserApiRequest;
import com.pozafly.tripllo.model.response.UserApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<Message> readUser(String id);
    public ResponseEntity<Message> rtnIdValid(String id);
    public ResponseEntity<Message> createUser(UserApiRequest request);
    public ResponseEntity<Message> updateUser(UserApiRequest request);
    public ResponseEntity<Message> deleteUser(String id);
}
