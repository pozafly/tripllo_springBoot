package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.common.config.utils.JwtTokenProvider;
import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.request.LoginApiRequest;
import com.pozafly.tripllo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public String createToken(LoginApiRequest loginRequest) {
        User user = userDao.readUser(loginRequest.getId());

        if (checkPassword(user, loginRequest.getPassword())) {  // 유저가 보유한 패스워드와 입력받은 패스워드가 일치하는 지 확인한다.
            log.info("비번이 틀립니다.");
            return null;
        }

        return jwtTokenProvider.createToken(loginRequest.getId()); // id 정보만 가지고 token을 만든다.
    }

    Boolean checkPassword(User user, String pw) {
        return user.getId().equals(pw);
    }

}
