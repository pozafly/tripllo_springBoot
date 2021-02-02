package com.pozafly.tripllo.boardHasLike.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BoardHasLikeService {

    public ResponseEntity<Message> createBoardHasLike(Map<String, Object> info);
    public ResponseEntity<Message> deleteBoardHasLike(Map<String, Object> info);

}
