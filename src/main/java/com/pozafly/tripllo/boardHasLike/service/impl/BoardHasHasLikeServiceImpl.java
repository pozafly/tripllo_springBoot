package com.pozafly.tripllo.boardHasLike.service.impl;

import com.pozafly.tripllo.boardHasLike.dao.BoardHasLikeDao;
import com.pozafly.tripllo.boardHasLike.service.BoardHasLikeService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class BoardHasHasLikeServiceImpl implements BoardHasLikeService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private BoardHasLikeDao boardHasLikeDao;

    @Override
    @Transactional
    public ResponseEntity<Message> createBoardHasLike(Map<String, Object> info) {
        if(!StringUtils.isEmpty(info.get("boardId"))) {
            boardHasLikeDao.createBoardHasLike(info);

            Map<String, Object> map = new HashMap<>();
            map.put("boardId", info.get("boardId"));
            map.put("likeCount", info.get("likeCount"));

            boardHasLikeDao.updateBoardLikeCount(map);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_LIKE);
            message.setData(info);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Message> deleteBoardHasLike(Map<String, Object> info) {
        try{
            boardHasLikeDao.deleteBoardHasLike(info);

            Map<String, Object> map = new HashMap<>();
            map.put("boardId", info.get("boardId"));
            map.put("likeCount", info.get("likeCount"));

            boardHasLikeDao.updateBoardLikeCount(map);

            Map<String, Object> rtnMap = new HashMap<>();
            rtnMap.put("likeInfo", info);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_LIKE);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUNT_LIKE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

}
