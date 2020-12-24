package com.pozafly.tripllo.card.service;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

public interface CardService {
    public ResponseEntity<Message> createCard(Card card);
    public ResponseEntity<Message> readCard(Long cardId);
    public ResponseEntity<Message> updateCard(Card card);
    public ResponseEntity<Message> deleteCard(Long cardId);
}
