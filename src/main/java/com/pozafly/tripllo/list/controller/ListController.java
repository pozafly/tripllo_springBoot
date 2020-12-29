package com.pozafly.tripllo.list.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.model.Lists;
import com.pozafly.tripllo.list.service.ListService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(value = "List V1")
@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    ListService listService;

    @ApiOperation(value = "리스트 생성", notes = "보드와 카드 중간에 위치한 리스트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 생성 성공"),
            @ApiResponse(code = 400, message = "리스 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createList(
            @ApiParam(value = "리스트의 보드 id", required = true)
            @RequestParam Long boardId,
            @ApiParam(value = "리스트 타이틀")
            @RequestParam(required = false) String title,
            @ApiParam(value = "리스트 포지션")
            @RequestParam(required = false) Double pos,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("boardId", boardId);
        map.put("title", title);
        map.put("pos", pos);

        return listService.createList(map);
    }

    @ApiOperation(value = "리스트 정보 수정", notes = "타이틀과 position을 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 정보 수정 성공"),
            @ApiResponse(code = 404, message = "리스트를 찾을 수 없습니다.")
    })
    @PutMapping("{listId}")
    public ResponseEntity<Message> updateList(
            @ApiParam(value = "리스트 수정 폼", required = true)
            @PathVariable Long listId,
            @ApiParam(value = "리스트 타이틀")
            @RequestParam(required = false) String title,
            @ApiParam(value = "리스트 포지션")
            @RequestParam(required = false) Double pos,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("listId", listId);
        map.put("title", title);
        map.put("pos", pos);

        return listService.updateList(map);
    }

    @ApiOperation(value = "리스트 삭제", notes = "리스트 id로 리스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 삭제 성공"),
            @ApiResponse(code = 404, message = "리스트를 찾을 수 없습니다.")
    })
    @DeleteMapping("{listId}")
    public ResponseEntity<Message> deleteList(
            @ApiParam(value = "리스트 id", required = true)
            @PathVariable Long listId
    ) {
        return listService.deleteList(listId);
    }

}
