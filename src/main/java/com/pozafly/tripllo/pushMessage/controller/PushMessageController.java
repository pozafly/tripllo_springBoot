package com.pozafly.tripllo.pushMessage.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import com.pozafly.tripllo.pushMessage.model.PushMessage;
import com.pozafly.tripllo.pushMessage.service.PushMessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "PushMessage V1")
@RestController
@RequestMapping("/api/pushMessage")
public class PushMessageController {

    @Autowired
    private PushMessageService pushMessageService;

    @ApiOperation(value = "푸시메세지 리스트 조회", notes = "id로 푸시메세지 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "푸시메세지 조회 성공"),
            @ApiResponse(code = 404, message = "푸시메세지 조회 불가능")
    })
    @GetMapping("{targetId}")
    public ResponseEntity<Message> readPushMessage(
            @ApiParam(value = "받는 사람 id", required = true, example = "1")
            @PathVariable String targetId
    ) {
        return pushMessageService.readPushMessage(targetId);
    }

    @ApiOperation(value = "푸시메세지 생성", notes = "새로운 푸시메세지 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "푸시메세지 생성 성공"),
            @ApiResponse(code = 400, message = "푸시메세지 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createPushMessage(
            @ApiParam(value = "푸시메세지 생성 폼")
            @RequestBody PushMessage pushMessage,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("targetId", pushMessage.getTargetId());
        map.put("boardId", pushMessage.getBoardId());
        map.put("content", pushMessage.getContent());

        return pushMessageService.createPushMessage(map);
    }

    @ApiOperation(value = "푸시메세지 정보 수정", notes = "isRead 수정만 가능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "푸시메세지 정보 수정 성공"),
            @ApiResponse(code = 404, message = "푸시메세지 찾을 수 없습니다.")
    })
    @PutMapping("")
    public ResponseEntity<Message> updatePushMessage(
            @ApiParam(value = "푸시메세지 생성 폼")
            @RequestBody PushMessage pushMessage,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("id", pushMessage.getId());
        map.put("isRead", pushMessage.getIsRead());

        return pushMessageService.updatePushMessage(map);
    }

    @ApiOperation(value = "푸시메세지 삭제", notes = "푸시메세지 id로 푸시메세지 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "푸시메세지 삭제 성공"),
            @ApiResponse(code = 404, message = "푸시메세지 찾을 수 없습니다.")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Message> deletePushMessage(
            @ApiParam(value = "푸시메세지 id", required = true, example = "2")
            @PathVariable Long id
    ) {
        return pushMessageService.deletePushMessage(id);
    }
}
