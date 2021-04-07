package com.pozafly.tripllo.user.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.common.security.JwtTokenProvider;
import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import com.pozafly.tripllo.user.model.request.LoginApiRequest;
import com.pozafly.tripllo.user.model.response.LoginApiResponse;
import com.pozafly.tripllo.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Message> login(LoginApiRequest request) {
        User user = userDao.readUser(request.getId());
        if (!ObjectUtils.isEmpty(user)) {

            boolean check = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if (check) {  // 유저가 보유한 패스워드와 입력받은 패스워드가 일치하는 지 확인한다.
                log.info("로그인 성공");

                List<String> roles = new ArrayList<>();
                roles.add("ROLE_USER");

                String token = jwtTokenProvider.createToken(user.getId(), roles); // id, role 정보만 가지고 token을 만든다.
                LoginApiResponse response = new LoginApiResponse(
                        token, user.getId(), user.getEmail(), user.getName(), user.getPicture(), user.getSocial(), user.getBio(), user.getRecentBoard(), user.getInvitedBoard(),
                        user.getCreatedAt(), user.getCreatedBy(), user.getUpdatedAt(), user.getUpdatedBy()
                );

                headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
                message.setStatus(StatusEnum.OK);
                message.setMessage(ResponseMessage.LOGIN_SUCCESS);
                message.setData(response);

                return new ResponseEntity<>(message, headers, HttpStatus.OK);
            } else {
                log.info("비번이 틀립니다");
                message.setStatus(StatusEnum.NOT_FOUND);
                message.setMessage(ResponseMessage.PASSWORD_WRONG);
                return new ResponseEntity<>(message, headers, HttpStatus.FORBIDDEN);   // 403
            }
        } else {
            log.info("해당 id가 없습니다.");
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_SIGNUP_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.FORBIDDEN);  // 403
        }

    }

    @Override
    public ResponseEntity<Message> socialLogin(String userId) {
        User user = userDao.readUser(userId);

        if (!ObjectUtils.isEmpty(user)) {
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            String token = jwtTokenProvider.createToken(user.getId(), roles); // id, role 정보만 가지고 token을 만든다.
            LoginApiResponse response = new LoginApiResponse(
                    token, user.getId(), user.getEmail(), user.getName(), user.getPicture(), user.getSocial(), user.getBio(), user.getRecentBoard(), user.getInvitedBoard(),
                    user.getCreatedAt(), user.getCreatedBy(), user.getUpdatedAt(), user.getUpdatedBy()
            );

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.LOGIN_SUCCESS);
            message.setData(response);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            log.info("해당 id가 없습니다.");
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.FORBIDDEN);  // 403
        }
    }

}
