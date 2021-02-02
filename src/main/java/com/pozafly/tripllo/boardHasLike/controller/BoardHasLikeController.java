package com.pozafly.tripllo.boardHasLike.controller;

import com.pozafly.tripllo.boardHasLike.model.BoardHasLike;
import com.pozafly.tripllo.boardHasLike.service.BoardHasLikeService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "BoardHasLike V1")
@RestController
@RequestMapping("/api/boardHasLike")
public class BoardHasLikeController {

    @Autowired
    private BoardHasLikeService boardHasLikeService;

    @ApiOperation(value = "like 생성", notes = "새로운 like를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 생성 성공"),
            @ApiResponse(code = 400, message = "카드 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createBoardHasLike(
            @ApiParam(value = "like 생성 폼")
            @RequestBody BoardHasLike boardHasLike,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("boardId", boardHasLike.getBoardId());
        map.put("likeCount", boardHasLike.getLikeCount());

        return boardHasLikeService.createBoardHasLike(map);
    }

    @ApiOperation(value = "like 삭제", notes = "like를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "like 삭제 성공"),
            @ApiResponse(code = 404, message = "like를 찾을 수 없습니다.")
    })
    @DeleteMapping("{boardId}/{likeCount}")
    public ResponseEntity<Message> deleteBoardHasLike(
            @ApiParam(value = "like id", required = true, example = "2")
            @PathVariable Long boardId,
            @PathVariable int likeCount,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("boardId", boardId);
        map.put("likeCount", likeCount);

        return boardHasLikeService.deleteBoardHasLike(map);
    }

}
