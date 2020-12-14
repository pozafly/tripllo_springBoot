package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.common.config.utils.JwtTokenProvider;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.request.LoginApiRequest;
import com.pozafly.tripllo.model.response.LoginApiResponse;
import com.pozafly.tripllo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    UserDao userDao;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<Message> createToken(LoginApiRequest loginRequest) {
        User user = userDao.readUser(loginRequest.getId());

        if (checkPassword(user, loginRequest.getPassword())) {  // 유저가 보유한 패스워드와 입력받은 패스워드가 일치하는 지 확인한다.
            String token = jwtTokenProvider.createToken(loginRequest.getId()); // id 정보만 가지고 token을 만든다.
            LoginApiResponse response = new LoginApiResponse(token, loginRequest.getId());

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.LOGIN_SUCCESS);
            message.setData(response);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            log.info("비번이 틀립니다.");
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.LOGIN_FAIL);
            return new ResponseEntity<>(message, headers, HttpStatus.FORBIDDEN);
        }
    }

    Boolean checkPassword(User user, String pw) {
        System.out.println("@@@@");
        System.out.println(user.getPassword().equals(pw));
        return user.getPassword().equals(pw);
    }

}
