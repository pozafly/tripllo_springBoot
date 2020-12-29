package com.pozafly.tripllo.board.controller;

import com.pozafly.tripllo.board.service.BoardService;
import com.pozafly.tripllo.common.domain.network.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Board V1")
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @ApiOperation(value = "보드 목록 조회", notes = "유저 ID로 Board 목록을 조회 합니다. 메인 페이지에서 보드를 선택할 때 사용합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 상세 정보 조회 성공"),
            @ApiResponse(code = 404, message = "보드 상세를 조회할 수 없습니다.")
    })
    @GetMapping("{userId}")
    public ResponseEntity<Message> readBoardList(
            @ApiParam(value = "유저 id", required = true, example = "pain103")
            @PathVariable String userId
    ) {
        return boardService.readBoardList(userId);
    }

    @ApiOperation(value = "보드 상세 조회", notes = "페이지에서 보드를 눌러 상세 페이지로 들어갔을 때 보드 id로 연관된 list와 card를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 상세 정보 조회 성공"),
            @ApiResponse(code = 404, message = "보드 상세를 조회할 수 없습니.")
    })
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Message> readBoardDetail(
            @ApiParam(value = "보드 id", required = true, example = "1")
            @PathVariable Long boardId
    ) {
        return boardService.readBoardDetail(boardId);
    }

    @ApiOperation(value = "보드 생성", notes = "새로운 보드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 생성 성공"),
            @ApiResponse(code = 400, message = "보드 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createBoard(
            @ApiParam(value = "보드 타이틀", required = true)
            @RequestParam String title,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("title", title);

        return boardService.createBoard(map);
    }

    @ApiOperation(value = "보드 정보 수정", notes = "보드 title, backgroundColor를 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 정보 수정 성공"),
            @ApiResponse(code = 404, message = "보드를 찾을 수 없습니다.")
    })
    @PutMapping("{boardId}")
    public ResponseEntity<Message> updateBoard(
            @ApiParam(value = "보드 id", required = true)
            @PathVariable Long boardId,
            @ApiParam(value = "보드 타이틀")
            @RequestParam(required = false) String title,
            @ApiParam(value = "보드 백그라운드 컬러")
            @RequestParam(required = false) String bgColor,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("boardId", boardId);
        map.put("title", title);
        map.put("bgColor", bgColor);

        return boardService.updateBoard(map);
    }

    @ApiOperation(value = "보드 삭제", notes = "보드id로 보드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "보드 삭제 성공"),
            @ApiResponse(code = 404, message = "보드를 찾을 수 없습니다.")
    })
    @DeleteMapping("{boardId}")
    public ResponseEntity<Message> deleteBoard(
            @ApiParam(value = "보드 id", required = true)
            @PathVariable Long boardId
    ) {
        return boardService.deleteBoard(boardId);
    }

}
