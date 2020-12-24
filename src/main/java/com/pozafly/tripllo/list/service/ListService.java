package com.pozafly.tripllo.list.service;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.model.Lists;
import org.springframework.http.ResponseEntity;

public interface ListService {
    public ResponseEntity<Message> createList(Lists list);
    public ResponseEntity<Message> updateList(Lists list);
    public ResponseEntity<Message> deleteList(Long listId);
}
