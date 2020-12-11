package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.network.Message;
import com.pozafly.tripllo.model.network.ResponseMessage;
import com.pozafly.tripllo.model.network.StatusEnum;
import com.pozafly.tripllo.model.request.UserApiRequest;
import com.pozafly.tripllo.model.response.UserApiResponse;
import com.pozafly.tripllo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "User V1")
@RequestMapping("/user")
public class UserController {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    UserService userService;

    @ApiOperation(value = "readUser", notes = "User Data 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 조회 성공"),
            @ApiResponse(code = 404, message = "회원을 찾을 수 없습니다.")
    })
    @GetMapping("{id}")
    public ResponseEntity<Message> readUser(
            @ApiParam(value = "아이디로 유저 정보 조회", required = true, example = "Guest01")
            @PathVariable String id
    ) {
        UserApiResponse userInfo = userService.readUser(id);

        if (!StringUtils.isEmpty(userInfo.getId())) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_USER);
            message.setData(userInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "userIdvValid", notes = "회원가입 가능한 ID인지 판별")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 가능한 ID 입니다."),
            @ApiResponse(code = 401, message = "이미 회원 ID가 사용되고 있습니다.")
    })
    @GetMapping("/valid/{id}")
    public ResponseEntity<Message> userIdvValid(
            @ApiParam(value = "회원가입 가능한 ID", required = true, example = "Guest01")
            @PathVariable String id
    ) {
        Boolean valid = userService.userIdValid(id);
        Map<String, Boolean> rtnMap = new HashMap<>();

        if (valid) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.POSSIBLE_CREATE);
            message.setData(rtnMap.put("value", true));

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.ALREADY_USE);
            message.setData(rtnMap.put("value", false));
            return new ResponseEntity<>(message, headers, HttpStatus.UNAUTHORIZED);
        }
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
        UserApiResponse userInfo = userService.createUser(request);

        if (!ObjectUtils.isEmpty(userInfo)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_USER);
            message.setData(userInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.ALREADY_USE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
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
        UserApiResponse userInfo = userService.updateUser(request);

        if (!ObjectUtils.isEmpty(userInfo)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_USER);
            message.setData(userInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
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
        UserApiResponse userInfo = userService.deleteUser(id);

        if (!ObjectUtils.isEmpty(userInfo)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_USER);
            message.setData(userInfo.getId());

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
