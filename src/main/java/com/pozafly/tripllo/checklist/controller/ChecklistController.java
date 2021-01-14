package com.pozafly.tripllo.checklist.controller;

import com.pozafly.tripllo.checklist.model.Checklist;
import com.pozafly.tripllo.checklist.service.ChecklistService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Checklist V1")
@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {

    @Autowired
    ChecklistService checklistService;

    @ApiOperation(value = "체크리스트 조회", notes = "체크리스트 id로 체크리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 조회 성공"),
            @ApiResponse(code = 404, message = "체크리스트 조회 불가능")
    })
    @GetMapping("{cardId}")
    public ResponseEntity<Message> readChecklist(
            @ApiParam(value = "체크리스트 id", required = true, example = "1")
            @PathVariable Long cardId
    ) {
        return checklistService.readChecklist(cardId);
    }

    @ApiOperation(value = "체크리스트 생성", notes = "새로운 체크리스트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 생성 성공"),
            @ApiResponse(code = 400, message = "체크리스트 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createChecklist(
            @ApiParam(value = "체크리스트 생성 폼")
            @RequestBody Checklist checklist,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("cardId", checklist.getCardId());
        map.put("title", checklist.getTitle());

        return checklistService.createChecklist(map);
    }

    @ApiOperation(value = "체크리스트 정보 수정", notes = "position, 설명, 라벨컬러, 위치 등을 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 정보 수정 성공"),
            @ApiResponse(code = 404, message = "체크리스트를 찾을 수 없습니다.")
    })
    @PutMapping("{checklistId}")
    public ResponseEntity<Message> updateChecklist(
            @ApiParam(value = "체크리스트 id", required = true)
            @PathVariable Long checklistId,
            @ApiParam(value = "타이틀", example = "가보자")
            @RequestBody Checklist checklist,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("checklistId", checklistId);
        map.put("title", checklist.getTitle());

        return checklistService.updateChecklist(map);
    }

    @ApiOperation(value = "체크리스트 삭제", notes = "체크리스트 id로 카드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 삭제 성공"),
            @ApiResponse(code = 404, message = "체크리스트를 찾을 수 없습니다.")
    })
    @DeleteMapping("{checklistId}/{cardId}")
    public ResponseEntity<Message> deleteChecklist(
            @ApiParam(value = "체크리스트 id", required = true, example = "2")
            @PathVariable Long checklistId,
            @ApiParam(value = "카드 id", required = true, example = "2")
            @PathVariable Long cardId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();
        return checklistService.deleteChecklist(checklistId, cardId, userId);
    }
}
