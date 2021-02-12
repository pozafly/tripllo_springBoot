package com.pozafly.tripllo.hashtag.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import com.pozafly.tripllo.hashtag.service.HashtagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Hashtag V1")
@RestController
@RequestMapping("/api/hashtag")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @ApiOperation(value = "해시태그 Board 조회", notes = "해시태그 name으로 보드, 해시태그를 조회 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "해시태그 조회 성공"),
            @ApiResponse(code = 404, message = "해시태그 조회 불가능")
    })
    @GetMapping("")
    public ResponseEntity<Message> readBoardByHashtag(
            @RequestParam String hashtagName,
            @RequestParam String lastLikeCount,
            @RequestParam String lastCreatedAt,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("hashtagName", hashtagName);
        map.put("lastLikeCount", lastLikeCount);
        map.put("lastCreatedAt", lastCreatedAt);

        return hashtagService.readBoardByHashtag(map);
    }

    @ApiOperation(value = "해시태그 count 조회", notes = "해시태그 name으로 보드, 해시태그를 조회 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "해시태그 조회 성공"),
            @ApiResponse(code = 404, message = "해시태그 조회 불가능")
    })
    @GetMapping("/orderByCount")
    public ResponseEntity<Message> readHashtagOrderByCount() {

        return hashtagService.readHashtagOrderByCount();
    }

}
