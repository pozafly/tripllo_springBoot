package com.pozafly.tripllo.board.service;

import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    public ResponseEntity<Message> readBoardList(String userId);
    public ResponseEntity<Message> readBoardDetail(Long boardId);
    public ResponseEntity<Message> createBoard(Board board);
    public ResponseEntity<Message> updateBoard(Board board);
    public ResponseEntity<Message> deleteBoard(Long boardId);
}
