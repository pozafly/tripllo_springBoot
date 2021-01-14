package com.pozafly.tripllo.checklist.service.impl;

import com.pozafly.tripllo.checklist.dao.ChecklistItemDao;
import com.pozafly.tripllo.checklist.service.ChecklistItemService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChecklistItemServiceImpl implements ChecklistItemService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private ChecklistItemDao checklistItemDao;

    @Override
    public ResponseEntity<Message> createChecklistItem(Map<String, Object> checklistItemInfo) {
        if(!StringUtils.isEmpty(checklistItemInfo.get("checklistId"))) {
            checklistItemDao.createChecklistItem(checklistItemInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_CHECKLIST_ITEM);
            message.setData(checklistItemInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updateChecklistItem(Map<String, Object> checklistItemInfo) {
        if(!StringUtils.isEmpty(checklistItemInfo.get("checklistItemId"))) {
            checklistItemDao.updateChecklistItem(checklistItemInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_CHECKLIST_ITEM);
            message.setData(checklistItemInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CHECKLIST_ITEM);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteChecklistItem(Long id) {
        try{
            checklistItemDao.deleteChecklistItem(id);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("checklistItemId", id);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_CHECKLIST_ITEM);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CHECKLIST_ITEM);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
