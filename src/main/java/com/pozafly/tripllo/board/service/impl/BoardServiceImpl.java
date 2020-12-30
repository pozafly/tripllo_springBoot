package com.pozafly.tripllo.board.service.impl;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.responseBoardDetail.BoardResultMap;
import com.pozafly.tripllo.board.model.responseBoardDetail.ResponseBoardDetail;
import com.pozafly.tripllo.board.service.BoardService;
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
public class BoardServiceImpl implements BoardService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    BoardDao boardDao;

    @Override
    public ResponseEntity<Message> readBoardList(String userId) {

        List<Board> board = boardDao.readBoardList(userId);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage(ResponseMessage.READ_BOARD);
        message.setData(board);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Message> readBoardDetail(Long boardId) {
        BoardResultMap item = boardDao.readBoardDetail(boardId);

        if(!ObjectUtils.isEmpty(item)) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD_DETAIL);
            message.setData(item);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> createBoard(Map<String, Object> boardInfo) {
        if(!StringUtils.isEmpty(boardInfo.get("title"))) {
            boardDao.createBoard(boardInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_BOARD);
            message.setData(boardInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updateBoard(Map<String, Object> boardInfo) {
        try{
            boardDao.updateBoard(boardInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_BOARD);
            message.setData(boardInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch(Exception e) {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteBoard(Long boardId) {
        try {
            boardDao.deleteBoard(boardId);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("boardId", boardId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_BOARD);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch(Exception e) {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
