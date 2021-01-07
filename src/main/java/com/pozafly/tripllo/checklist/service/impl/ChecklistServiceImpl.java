package com.pozafly.tripllo.checklist.service.impl;

import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.checklist.dao.ChecklistDao;
import com.pozafly.tripllo.checklist.model.response.ChecklistResultMap;
import com.pozafly.tripllo.checklist.service.ChecklistService;
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
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChecklistServiceImpl implements ChecklistService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private ChecklistDao checklistDao;
    @Autowired
    private CardDao cardDao;

    @Override
    public ResponseEntity<Message> createChecklist(Map<String, Object> checklistInfo) {
        if(!StringUtils.isEmpty(checklistInfo.get("cardId"))) {
            checklistDao.createChecklist(checklistInfo);

            // 맨 처음 만들 때 하나만 있는 것이 카드의 isChecklist 를 Y로 침.
            if(checklistSize((Long) checklistInfo.get("cardId")) == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", checklistInfo.get("userId"));
                map.put("cardId", checklistInfo.get("cardId"));
                map.put("isChecklist", "Y");

                cardDao.updateCard(map);
            }

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_CHECKLIST);
            message.setData(checklistInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readChecklist(Long cardId) {
        List<ChecklistResultMap> checklist = checklistDao.readChecklist(cardId);

        if (!ObjectUtils.isEmpty(checklist)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_CHECKLIST);
            message.setData(checklist);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_CHECKLIST);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> updateChecklist(Map<String, Object> checklistInfo) {
        if(!StringUtils.isEmpty(checklistInfo.get("checklistId"))) {
            checklistDao.updateChecklist(checklistInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_CHECKLIST);
            message.setData(checklistInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CHECKLIST);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteChecklist(Long checklistId, Long cardId, String userId) {
        try{
            checklistDao.deleteChecklist(checklistId);

            // 지우고 나서 count가 0 이면 N으로.
            if(checklistSize(cardId) == 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("cardId", cardId);
                map.put("isChecklist", "N");

                cardDao.updateCard(map);
            }

            Map<String, Long> rtnMap = new HashMap<>();

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_CHECKLIST);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CHECKLIST);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    private int checklistSize(Long cardId) {
        return checklistDao.countChecklist(cardId);
    }
}
