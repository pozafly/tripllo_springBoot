package com.pozafly.tripllo.checklist.controller;

import com.pozafly.tripllo.checklist.model.ChecklistItem;
import com.pozafly.tripllo.checklist.service.ChecklistItemService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "ChecklistItem V1")
@RestController
@RequestMapping("/api/checklistItem")
public class ChecklistItemController {

    @Autowired
    private ChecklistItemService checklistItemService;

    @ApiOperation(value = "체크리스트 아이템 생성", notes = "새로운 체크리스트 아이템를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 아이템 생성 성공"),
            @ApiResponse(code = 400, message = "체크리스트 아이템 생성 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createChecklistItem(
            @ApiParam(value = "체크리스트 아이템 생성 폼")
            @RequestBody ChecklistItem checklistItem,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("checklistId", checklistItem.getChecklistId());
        map.put("item", checklistItem.getItem());

        return checklistItemService.createChecklistItem(map);
    }

    @ApiOperation(value = "체크리스트 아이템 정보 수정", notes = "position, 설명, 라벨컬러, 위치 등을 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 아이템 정보 수정 성공"),
            @ApiResponse(code = 404, message = "체크리스트 아이템을 찾을 수 없습니다.")
    })
    @PutMapping("{checklistItemId}")
    public ResponseEntity<Message> updateChecklistItem(
            @ApiParam(value = "체크리스트 아이템 id", required = true)
            @PathVariable Long checklistItemId,
            @ApiParam(value = "체크리스트 아이템 폼", example = "가보자")
            @RequestBody ChecklistItem checklistItem,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("checklistItemId", checklistItemId);
        map.put("item", checklistItem.getItem());
        map.put("isChecked", checklistItem.getIsChecked());

        return checklistItemService.updateChecklistItem(map);
    }

    @ApiOperation(value = "체크리스트 아이템 삭제", notes = "체크리스트 아이템 id로 카드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "체크리스트 아이템 삭제 성공"),
            @ApiResponse(code = 404, message = "체크리스트 아이템을 찾을 수 없습니다.")
    })
    @DeleteMapping("{checklistItemId}")
    public ResponseEntity<Message> deleteChecklistItem(
            @ApiParam(value = "체크리스트 아이템 id", required = true, example = "2")
            @PathVariable Long checklistItemId
    ) {
        return checklistItemService.deleteChecklistItem(checklistItemId);
    }
}
