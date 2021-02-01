package com.pozafly.tripllo.hashtag.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.hashtag.service.HashtagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Hashtag V1")
@RestController
@RequestMapping("/api/hashtag")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @ApiOperation(value = "해시태그 조회", notes = "해시태그 name으로 보드, 해시태그를 조회 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "해시태그 조회 성공"),
            @ApiResponse(code = 404, message = "해시태그 조회 불가능")
    })
    @GetMapping("{name}")
    public ResponseEntity<Message> readBoardByHashtag(
            @ApiParam(value = "해시태그 name", required = true, example = "여행")
            @PathVariable String name
    ) {
        return hashtagService.readBoardByHashtag(name);
    }

}
