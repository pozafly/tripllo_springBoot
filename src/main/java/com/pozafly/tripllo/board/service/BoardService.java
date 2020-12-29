package com.pozafly.tripllo.board.service;

import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BoardService {
    public ResponseEntity<Message> readBoardList(String userId);
    public ResponseEntity<Message> readBoardDetail(Long boardId);
    public ResponseEntity<Message> createBoard(Map<String, String> boardInfo);
    public ResponseEntity<Message> updateBoard(Map<String, Object> boardInfo);
    public ResponseEntity<Message> deleteBoard(Long boardId);
}
