package com.pozafly.tripllo.board.service.impl;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.response.BoardApiDetailResponse;
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

        if (!ObjectUtils.isEmpty(board)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(board);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readBoardDetail(Long boardId) {
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(boardId);
//        List<BoardApiDetailResponse> response = boardDao.readBoardDetail(boardId);
//        System.out.println("@@@@@@@@@@");
//        System.out.println(response);
//
//        Map<String, Object> item = new HashMap<>();
        //List<>

//        for(BoardApiDetailResponse item : response) {
//            System.out.println(item);
//        }
        return null;
    }

    @Override
    public ResponseEntity<Message> createBoard(Board board) {
        return null;
    }

    @Override
    public ResponseEntity<Message> updateBoard(Board board) {
        return null;
    }

    @Override
    public ResponseEntity<Message> deleteBoard(Long boardId) {
        return null;
    }
}
