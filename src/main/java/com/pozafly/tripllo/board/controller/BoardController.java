package com.pozafly.tripllo.board.controller;

import com.pozafly.tripllo.board.service.BoardService;
import com.pozafly.tripllo.common.domain.network.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Board V1")
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boradService;

    @ApiOperation(value = "readBoardList", notes = "유저 ID로 Board 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 상세 정보 조회 성공"),
            @ApiResponse(code = 404, message = "보드 상세를 조회할 수 없습니.")
    })
    @GetMapping("{userId}")
    public ResponseEntity<Message> readBoardList(
            @ApiParam(value = "유저 id", required = true, example = "pain103")
            @PathVariable String userId
    ) {
        return boradService.readBoardList(userId);
    }

    @ApiOperation(value = "readBoardDetail", notes = "보드 id로 상세 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 상세 정보 조회 성공"),
            @ApiResponse(code = 404, message = "보드 상세를 조회할 수 없습니.")
    })
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Message> readBoardDetail(
            @ApiParam(value = "보드 id", required = true, example = "1")
            @PathVariable Long boardId
    ) {
        boradService.readBoardDetail(boardId);
        return null;
    }
}
