package com.pozafly.tripllo.comment.controller;

import com.pozafly.tripllo.comment.model.Comment;
import com.pozafly.tripllo.comment.service.CommentService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Comment V1")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "코멘트 조회", notes = "카드id로 코멘트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "코멘트 조회 성공"),
            @ApiResponse(code = 404, message = "코멘 조회 불가능")
    })
    @GetMapping("{cardId}")
    public ResponseEntity<Message> readComment(
            @ApiParam(value = "코멘 id", required = true, example = "1")
            @PathVariable Long cardId
    ) {
        return commentService.readComment(cardId);
    }

    @ApiOperation(value = "코멘트 생성", notes = "새로운 코멘트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "코멘트 생성 성공"),
            @ApiResponse(code = 400, message = "코멘트 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createComment(
            @ApiParam(value = "코멘 생성 폼")
            @RequestBody Comment comment,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("cardId", comment.getCardId());
        map.put("userId", userId);
        map.put("comment", comment.getComment());

        return commentService.createComment(map);
    }

    @ApiOperation(value = "코멘트 정보 수정", notes = "코멘트 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "코멘트 정보 수정 성공"),
            @ApiResponse(code = 404, message = "코멘트를 찾을 수 없습니다.")
    })
    @PutMapping("")
    public ResponseEntity<Message> updateComment(
            @ApiParam(value = "타이틀", example = "가보자")
            @RequestBody Comment comment,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentId", comment.getId());
        map.put("comment", comment.getComment());

        return commentService.updateComment(map);

    }

    @ApiOperation(value = "코멘트 삭제", notes = "카드id로 코멘트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "코멘트 삭제 성공"),
            @ApiResponse(code = 404, message = "코멘트를 찾을 수 없습니다.")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteComment(
            @ApiParam(value = "코멘트 id", required = true, example = "2")
            @PathVariable Long id
    ) {
        return commentService.deleteComment(id);
    }
}
