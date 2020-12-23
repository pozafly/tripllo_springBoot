package com.pozafly.tripllo.user.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.common.utils.PasswordUtil;
import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import com.pozafly.tripllo.user.model.response.UserApiResponse;
import com.pozafly.tripllo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<Message> readUser(String id) {

        User user = userDao.readUser(id);
        UserApiResponse userInfo = new UserApiResponse(user);

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

    @Override
    public ResponseEntity<Message> rtnIdValid(String id) {
        Map<String, Boolean> rtnMap = new HashMap<>();
        Boolean valid = userIdValid(id);

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

    @Override
    public ResponseEntity<Message> createUser(UserApiRequest request) {
        if(userIdValid(request.getId())) {

            // 소셜 로그인 패스워드 만들기
            if(!StringUtils.isEmpty(request.getSocialYn())) {
                PasswordUtil pw = new PasswordUtil();
                String newPw = pw.encryptSHA256(request.getId());
                request.setPassword(newPw);
            }
            userDao.createUser(request);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_USER);
            message.setData(request);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.ALREADY_USE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> updateUser(UserApiRequest request) {
        if(!userIdValid(request.getId())) {
            userDao.updateUser(request);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_USER);
            message.setData(request);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteUser(String id) {
        if(!userIdValid(id)) {
            userDao.deleteUser(id);

            Map<String, String> rtnMap = new HashMap<>();
            rtnMap.put("userId", id);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_USER);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }


    public Boolean userIdValid(String id) {
        User user = userDao.readUser(id);
        if (!ObjectUtils.isEmpty(user)) {
            return !user.getId().equals(id);
        } else {
            return true;
        }
    }
}
