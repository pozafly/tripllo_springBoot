package com.pozafly.tripllo.hashtag.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.hashtag.dao.HashtagDao;
import com.pozafly.tripllo.hashtag.model.response.ResponseHashtagByBoard;
import com.pozafly.tripllo.hashtag.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Service
public class HashtagServiceImpl implements HashtagService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private HashtagDao hashtagDao;

    @Override
    public ResponseEntity<Message> readBoardByHashtag(Map<String, String> info) {

        List<ResponseHashtagByBoard> boards = hashtagDao.readBoardByHashtag(info);

        if (!ObjectUtils.isEmpty(boards)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(boards);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

    }
}
