package com.pozafly.tripllo.user.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import com.pozafly.tripllo.user.model.request.ChangePwApiRequest;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import com.pozafly.tripllo.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "User V1")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "유저 조회", notes = "유저를 조회하는 API 입니다.")
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

    @ApiOperation(value = "유저 초대 조회", notes = "초대 가능한 유저를 조회하는 API 입니다. like 구문으로 조회할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 조회 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @GetMapping("/isInvite/{id}")
    public ResponseEntity<Message> readIsInviteUser(
            @ApiParam(value = "아이디로 초대 가능 유저 정보 조회", required = true, example = "pain103")
            @PathVariable String id
    ) {
        return userService.readIsInviteUser(id);
    }

    @ApiOperation(value = "초대된 유저 조회", notes = "초대된 유저를 조회해 사진을 표시하는 API 입니다. in 구문으로 조회 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 조회 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @GetMapping("/invited/{userList}")
    public ResponseEntity<Message> readInvitedUser(
            @ApiParam(value = "아이디로 초대 가능 유저 정보 조회", required = true, example = "pain103")
            @PathVariable String userList
    ) {
        return userService.readInvitedUser(userList);
    }

    @ApiOperation(value = "회원가입 가능 판별", notes = "회원가입 가능한 ID인지 판별")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 가능한 ID 입니다."),
            @ApiResponse(code = 401, message = "이미 회원 ID가 사용되고 있습니다.")
    })
    @GetMapping("/valid/{id}")
    public ResponseEntity<Message> rtnIdValid(
            @ApiParam(value = "회원가입 하고싶은 ID", required = true, example = "pain103")
            @PathVariable String id
    ) {
        return userService.rtnIdValid(id);

    }

    @ApiOperation(value = "유저 생성", notes = "유저를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 성공"),
            @ApiResponse(code = 400, message = "회원 가입 불가능")
    })
    @PostMapping("")
    public ResponseEntity<Message> createUser(
            @ApiParam(value = "회원가입 폼", required = true)
            @RequestBody UserApiRequest request
    ) {
        return userService.createUser(request);
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 수정 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @PutMapping("")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Message> updateUser(
            @ApiParam(value = "유저 폼")
            @RequestBody(required = false) UserApiRequest request
    ) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getId());
        map.put("email", request.getEmail());
        map.put("name", request.getName());
        map.put("password", request.getPassword());
        map.put("bio", request.getBio());
        map.put("picture", request.getPicture());
        map.put("recentBoard", request.getRecentBoard());
        map.put("invitedBoard", request.getInvitedBoard());
        System.out.println("@@@@@@@@@@@@@@");

        return userService.updateUser(map);

    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원을 탈퇴 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @DeleteMapping("{password}")
    public ResponseEntity<Message> deleteUser(
            @ApiParam(value = "회원 비밀번호", required = true)
            @PathVariable String password,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);

        return userService.deleteUser(map);
    }

    @PostMapping("/changePw")
    public ResponseEntity<Message> changePw(
            @RequestBody ChangePwApiRequest request,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        String userId = securityUser.getUsername();

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("currentPw", request.getCurrentPw());
        map.put("newPw", request.getNewPw());

        return userService.changePw(map);
    }
}
