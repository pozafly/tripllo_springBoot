package com.pozafly.tripllo.pushMessage.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PushMessageService {
    public ResponseEntity<Message> createPushMessage(Map<String, Object> pushMessageInfo);
    public ResponseEntity<Message> readPushMessage(String to);
    public ResponseEntity<Message> updatePushMessage(Map<String, Object> pushMessageInfo);
    public ResponseEntity<Message> deletePushMessage(Long id);
}
