package com.pozafly.tripllo.card.service;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CardService {
    public ResponseEntity<Message> createCard(Map<String, Object> cardInfo);
    public ResponseEntity<Message> readCard(Long cardId);
    public ResponseEntity<Message> updateCard(Map<String, Object> cardInfo);
    public ResponseEntity<Message> deleteCard(Long cardId);
}
