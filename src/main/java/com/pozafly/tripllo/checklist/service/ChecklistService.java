package com.pozafly.tripllo.checklist.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ChecklistService {
    public ResponseEntity<Message> createChecklist(Map<String, Object> checklistInfo);
    public ResponseEntity<Message> readChecklist(Long cardId);
    public ResponseEntity<Message> updateChecklist(Map<String, Object> checklistInfo);
    public ResponseEntity<Message> deleteChecklist(Long checklistId, Long cardId, String userId);
}
