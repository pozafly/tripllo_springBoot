package com.pozafly.tripllo.card.service.impl;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.card.service.CardService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    CardDao cardDao;

    @Override
    public ResponseEntity<Message> createCard(Card card) {
        if(!ObjectUtils.isEmpty(card)) {
            cardDao.createCard(card);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_CARD);
            message.setData(card);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readCard(Long cardId) {
        Card card = cardDao.readCard(cardId);

        if (!ObjectUtils.isEmpty(card)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_CARD);
            message.setData(card);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updateCard(Card card) {
        if(!ObjectUtils.isEmpty(card)) {
            cardDao.updateCard(card);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_CARD);
            message.setData(card);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteCard(Long cardId) {
        try{
            cardDao.deleteCard(cardId);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("listId", cardId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_CARD);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_LIST);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }

    }
}
