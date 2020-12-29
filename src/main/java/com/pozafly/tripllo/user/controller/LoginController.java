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

@Api(value = "Login V1")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "로그인 API", notes = "로그인하는 API 입니다. 사용자 아이디와 암호를 입력해야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 403, message = "비밀번호가 틀리거나 해당 id가 없습니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<Message> login(
            @ApiParam(value = "유저 로그인 폼", required = true)
            @RequestBody LoginApiRequest request,
            HttpServletRequest req
    ) {
        HttpSession session = req.getSession();
        session.setAttribute("userId", request.getId());

        return loginService.login(request);
    }

    @ApiOperation(value = "소셜 로그인 API", notes = "소셜 로그인하는 API 입니다. 소셜 로그인으로 ID, PW가 검증이 끝났으므로 따로 validation이 필요하지 않다고 판단했습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "소셜 로그인 성공"),
    })
    @GetMapping("/login/social/{userId}")
    public ResponseEntity<Message> socialLogin(
            @PathVariable String userId, HttpServletRequest req
    ) {
        HttpSession session = req.getSession();
        session.setAttribute("userId", userId);

        return loginService.socialLogin(userId);
    }

    @ApiOperation(value = "로그아웃 API", notes = "로그아웃. spring session 삭제로직.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공"),
    })
    @GetMapping("/logout")
    public void logout(
            HttpServletRequest req
    ) {
        System.out.println("로그아웃 진행한다.");
        HttpSession session = req.getSession();
        session.setAttribute("userId", null);
        session.invalidate();
    }

}
