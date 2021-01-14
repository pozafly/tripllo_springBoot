package com.pozafly.tripllo.card.controller;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.card.service.CardService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Card V1")
@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @ApiOperation(value = "카드 조회", notes = "카드id로 카드를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 조회 성공"),
            @ApiResponse(code = 404, message = "카드 조회 불가능")
    })
    @GetMapping("{cardId}")
    public ResponseEntity<Message> readCard(
            @ApiParam(value = "카드 id", required = true, example = "1")
            @PathVariable Long cardId
    ) {
        return cardService.readCard(cardId);
    }

    @ApiOperation(value = "카드 생성", notes = "새로운 카드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 생성 성공"),
            @ApiResponse(code = 400, message = "카드 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createCard(
            @ApiParam(value = "카드 생성 폼")
            @RequestBody Card card,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("listId", card.getListId());
        map.put("title", card.getTitle());
        map.put("pos", card.getPos());

        return cardService.createCard(map);
    }

    @ApiOperation(value = "카드 정보 수정", notes = "position, 설명, 라벨컬러, 위치 등을 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 정보 수정 성공"),
            @ApiResponse(code = 404, message = "카드를 찾을 수 없습니다.")
    })
    @PutMapping("{cardId}")
    public ResponseEntity<Message> updateCard(
            @ApiParam(value = "카드 id", required = true)
            @PathVariable Long cardId,
            @ApiParam(value = "타이틀", example = "가보자")
            @RequestBody Card card,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("cardId", cardId);
        map.put("listId", card.getListId());
        map.put("title", card.getTitle());
        map.put("pos", card.getPos());
        map.put("description", card.getDescription());
        map.put("labelColor", card.getLabelColor());
        map.put("location", card.getLocation());
        map.put("dueDate", card.getDueDate());

        return cardService.updateCard(map);

    }

    @ApiOperation(value = "카드 삭제", notes = "카드id로 카드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 삭제 성공"),
            @ApiResponse(code = 404, message = "카드를 찾을 수 없습니다.")
    })
    @DeleteMapping("{cardId}")
    public ResponseEntity<Message> deleteCard(
            @ApiParam(value = "카드 id", required = true, example = "2")
            @PathVariable Long cardId
    ) {
        return cardService.deleteCard(cardId);
    }

}
