package com.pozafly.tripllo.card.controller;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.card.service.CardService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.model.Lists;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            @ApiParam(value = "보드 리스트 id", required = true, example = "1")
            @RequestParam Long listId,
            @ApiParam(value = "타이틀", example = "가보자")
            @RequestParam(required = false) String title,
            @ApiParam(value = "포지션", example = "1")
            @RequestParam(required = false) String pos,
            @ApiParam(value = "설명", example = "이러이러한 카드이다.")
            @RequestParam(required = false) String description,
            @ApiParam(value = "라벨 컬러", example = "rgba()...")
            @RequestParam(required = false) String labelColor,
            @ApiParam(value = "위치", example = "대한민국")
            @RequestParam(required = false) String location,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("listId", listId);
        map.put("title", title);
        map.put("pos", pos);
        map.put("description", description);
        map.put("labelColor", labelColor);
        map.put("location", location);

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
            @RequestParam(required = false) String title,
            @ApiParam(value = "포지션", example = "1")
            @RequestParam(required = false) String pos,
            @ApiParam(value = "설명", example = "이러이러한 카드이다.")
            @RequestParam(required = false) String description,
            @ApiParam(value = "라벨 컬러", example = "rgba()...")
            @RequestParam(required = false) String labelColor,
            @ApiParam(value = "위치", example = "대한민국")
            @RequestParam(required = false) String location,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("cardId", cardId);
        map.put("title", title);
        map.put("pos", pos);
        map.put("description", description);
        map.put("labelColor", labelColor);
        map.put("location", location);

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
