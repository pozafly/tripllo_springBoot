package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.network.Message;
import com.pozafly.tripllo.model.network.ResponseMessage;
import com.pozafly.tripllo.model.network.StatusEnum;
import com.pozafly.tripllo.model.response.UserApiResponse;
import com.pozafly.tripllo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/user")
public class UserController {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<Message> getUser(@PathVariable String id) {
        UserApiResponse userInfo = userService.getUser(id);

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
}
