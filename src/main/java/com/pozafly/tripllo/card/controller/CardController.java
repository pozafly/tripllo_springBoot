package com.pozafly.tripllo.card.controller;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.card.service.CardService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.model.Lists;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Card V1")
@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @ApiOperation(value = "readCard", notes = "카드 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 조회 성공"),
            @ApiResponse(code = 400, message = "카드 조회 불가능")
    })
    @GetMapping("{cardId}")
    public ResponseEntity<Message> readCard(
            @ApiParam(value = "카드 조회 폼", required = true)
            @PathVariable Long cardId
    ) {
        return cardService.readCard(cardId);
    }

    @ApiOperation(value = "createCard", notes = "카드 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 생성 성공"),
            @ApiResponse(code = 400, message = "카드 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createCard(
            @ApiParam(value = "카드 생성 폼", required = true)
            @RequestBody Card card
    ) {
        return cardService.createCard(card);
    }

    @ApiOperation(value = "updateCard", notes = "카드 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 정보 수정 성공"),
            @ApiResponse(code = 400, message = "카드를 찾을 수 없습니다.")
    })
    @PutMapping("")
    public ResponseEntity<Message> updateCard(
            @ApiParam(value = "카드 수정 폼", required = true)
            @RequestBody Card card
    ) {
        return cardService.updateCard(card);
    }

    @ApiOperation(value = "deleteCard", notes = "카드 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카드 삭제 성공"),
            @ApiResponse(code = 400, message = "카드를 찾을 수 없습니다.")
    })
    @DeleteMapping("{cardId}")
    public ResponseEntity<Message> deleteCard(
            @ApiParam(value = "카드 id", required = true)
            @PathVariable Long cardId
    ) {
        return cardService.deleteCard(cardId);
    }

}
