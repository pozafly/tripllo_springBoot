package com.pozafly.tripllo.list.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.model.Lists;
import com.pozafly.tripllo.list.service.ListService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "List V1")
@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    ListService listService;

    @ApiOperation(value = "createList", notes = "리스트생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 생성 성공"),
            @ApiResponse(code = 400, message = "리스 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createList(
            @ApiParam(value = "리스트 생성 폼", required = true)
            @RequestBody Lists list
    ) {
        return listService.createList(list);
    }

    @ApiOperation(value = "updateList", notes = "리스트 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 정보 수정 성공"),
            @ApiResponse(code = 400, message = "리스트를 찾을 수 없습니다.")
    })
    @PutMapping("")
    public ResponseEntity<Message> updateList(
            @ApiParam(value = "리스트 수정 폼", required = true)
            @RequestBody Lists list
    ) {
        return listService.updateList(list);
    }

    @ApiOperation(value = "deleteList", notes = "리스트 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 삭제 성공"),
            @ApiResponse(code = 400, message = "리스트를 찾을 수 없습니다.")
    })
    @DeleteMapping("{listId}")
    public ResponseEntity<Message> deleteList(
            @ApiParam(value = "리스트 id", required = true)
            @PathVariable Long listId
    ) {
        return listService.deleteList(listId);
    }

}
