package com.pozafly.tripllo.user.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import com.pozafly.tripllo.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "User V1")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "readUser", notes = "User Data 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 조회 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @GetMapping("{id}")
    public ResponseEntity<Message> readUser(
            @ApiParam(value = "아이디로 유저 정보 조회", required = true, example = "pain103")
            @PathVariable String id
    ) {
        return userService.readUser(id);
    }

    @ApiOperation(value = "userIdvValid", notes = "회원가입 가능한 ID인지 판별")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 가능한 ID 입니다."),
            @ApiResponse(code = 401, message = "이미 회원 ID가 사용되고 있습니다.")
    })
    @GetMapping("/valid/{id}")
    public ResponseEntity<Message> ValidId(
            @ApiParam(value = "회원가입 가능한 ID", required = true, example = "pain103")
            @PathVariable String id
    ) {
        return userService.rtnIdValid(id);

    }

    @ApiOperation(value = "createUser", notes = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 성공"),
            @ApiResponse(code = 400, message = "회원가입 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createUser(
            @ApiParam(value = "회원가입 폼", required = true)
            @RequestBody UserApiRequest request
    ) {
        return userService.createUser(request);
    }

    @ApiOperation(value = "updateUser", notes = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 수정 성공"),
            @ApiResponse(code = 400, message = "회원을 찾을 수 없습니다.")
    })
    @PutMapping("")
    public ResponseEntity<Message> updateUser(
            @ApiParam(value = "회원 수정 폼", required = true)
            @RequestBody UserApiRequest request
    ) {
        return userService.updateUser(request);
    }

    @ApiOperation(value = "deleteUser", notes = "회원탈퇴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 400, message = "회원을 찾을 수 없습니다.")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUser(
            @ApiParam(value = "회원탈퇴 ID", required = true)
            @PathVariable String id
    ) {
        return userService.deleteUser(id);
    }
}
