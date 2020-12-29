package com.pozafly.tripllo.user.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.user.model.request.LoginApiRequest;
import com.pozafly.tripllo.user.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Login V1")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "로그인 API", notes = "로그인하는 API 입니다. 사용자 아이디와 암호를 입력해야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 403, message = "비밀번호가 틀리거나 해당 id가 없습니다.")
    })
    @PostMapping("")
    public ResponseEntity<Message> login(
            @ApiParam(value = "유저 id", required = true, example = "pain103")
            @RequestParam String id,
            @ApiParam(value = "유저 password", required = true, example = "1234")
            @RequestParam String password,
            HttpServletRequest req
    ) {
        HttpSession session = req.getSession();
        session.setAttribute("userId", id);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("password", password);

        return loginService.login(userInfo);
    }

    @ApiOperation(value = "소셜 로그인 API", notes = "소셜 로그인하는 API 입니다. 소셜 로그인으로 ID, PW가 검증이 끝났으므로 따로 validation이 필요하지 않다고 판단했습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "소셜 로그인 성공"),
    })
    @GetMapping("/social/{userId}")
    public ResponseEntity<Message> socialLogin(
            @PathVariable String userId, HttpServletRequest req
    ) {
        HttpSession session = req.getSession();
        session.setAttribute("userId", userId);
        return loginService.socialLogin(userId);
    }

}
