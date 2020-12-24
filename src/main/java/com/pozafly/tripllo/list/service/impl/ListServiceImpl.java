package com.pozafly.tripllo.list.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.list.dao.ListDao;
import com.pozafly.tripllo.list.model.Lists;
import com.pozafly.tripllo.list.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class ListServiceImpl implements ListService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    ListDao listDao;


    @Override
    public ResponseEntity<Message> createList(Lists list) {
        if(!ObjectUtils.isEmpty(list)) {
            listDao.createList(list);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_LIST);
            message.setData(list);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updateList(Lists list) {
        if(!ObjectUtils.isEmpty(list)) {
            listDao.updateList(list);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_LIST);
            message.setData(list);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteList(Long listId) {
        try{
            listDao.deleteList(listId);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("listId", listId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_LIST);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_LIST);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
