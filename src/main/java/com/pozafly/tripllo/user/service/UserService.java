package com.pozafly.tripllo.user.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    public ResponseEntity<Message> readUser(String id);
    public ResponseEntity<Message> readIsInviteUser(String id);
    public ResponseEntity<Message> readInvitedUser(String userList);
    public ResponseEntity<Message> rtnIdValid(String id);
    public ResponseEntity<Message> createUser(UserApiRequest request);
    public ResponseEntity<Message> updateUser(Map<String, Object> userInfo);
    public ResponseEntity<Message> deleteUser(Map<String, String> userInfo);
}
