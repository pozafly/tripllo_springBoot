package com.pozafly.tripllo.checklist.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ChecklistItemService {
    public ResponseEntity<Message> createChecklistItem(Map<String, Object> checklistItemInfo);
    public ResponseEntity<Message> updateChecklistItem(Map<String, Object> checklistItemInfo);
    public ResponseEntity<Message> deleteChecklistItem(Long id);
}
