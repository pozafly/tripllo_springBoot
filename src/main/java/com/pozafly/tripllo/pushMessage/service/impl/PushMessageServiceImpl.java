package com.pozafly.tripllo.pushMessage.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.pushMessage.dao.PushMessageDao;
import com.pozafly.tripllo.pushMessage.model.PushMessage;
import com.pozafly.tripllo.pushMessage.service.PushMessageService;
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
public class PushMessageServiceImpl implements PushMessageService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private PushMessageDao pushMessageDao;

    @Override
    public ResponseEntity<Message> createPushMessage(Map<String, Object> pushMessageInfo) {
        if(!StringUtils.isEmpty(pushMessageInfo.get("targetId"))) {
            pushMessageDao.createPushMessage(pushMessageInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_PUSH_MESSAGE);
            message.setData(pushMessageInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readPushMessage(String to) {
        List<PushMessage> pushMessage = pushMessageDao.readPushMessage(to);

        if (!ObjectUtils.isEmpty(pushMessage)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_PUSH_MESSAGE);
            message.setData(pushMessage);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_PUSH_MESSAGE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updatePushMessage(Map<String, Object> pushMessageInfo) {
        if(!StringUtils.isEmpty(pushMessageInfo.get("id"))) {
            pushMessageDao.updatePushMessage(pushMessageInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_PUSH_MESSAGE);
            message.setData(pushMessageInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_PUSH_MESSAGE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deletePushMessage(Long id) {
        try{
            pushMessageDao.deletePushMessage(id);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("id", id);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_PUSH_MESSAGE);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_PUSH_MESSAGE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
