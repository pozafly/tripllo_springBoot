package com.pozafly.tripllo.comment.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CommentService {
    public ResponseEntity<Message> createComment(Map<String, Object> commentInfo);
    public ResponseEntity<Message> readComment(Long cardId);
    public ResponseEntity<Message> updateComment(Map<String, Object> commentInfo);
    public ResponseEntity<Message> deleteComment(Long commentId, String userId);
}
