package com.pozafly.tripllo.list.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ListService {
    public ResponseEntity<Message> createList(Map<String, Object> listInfo);
    public ResponseEntity<Message> updateList(Map<String, Object> listInfo);
    public ResponseEntity<Message> deleteList(Long listId);
}
