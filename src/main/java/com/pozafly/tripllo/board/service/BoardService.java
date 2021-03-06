package com.pozafly.tripllo.board.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BoardService {
    public ResponseEntity<Message> readBoardOne(Long boardId);
    public ResponseEntity<Message> readPersonalBoardList(Map<String, String> boardInfo);
    public ResponseEntity<Message> readSearchUserBoard(Map<String, String> boardInfo);
    public ResponseEntity<Message> rerenderBoard(Map<String, Object> boardInfo);
    public ResponseEntity<Message> readRecentBoardList(Map<String, Object> recentInfo);
    public ResponseEntity<Message> readInvitedBoardList(Map<String, Object> invitedInfo);
    public ResponseEntity<Message> readBoardDetail(Long boardId);
    public ResponseEntity<Message> createBoard(Map<String, Object> boardInfo);
    public ResponseEntity<Message> updateBoard(Map<String, Object> boardInfo);
    public ResponseEntity<Message> deleteBoard(Long boardId);
}
