package com.pozafly.tripllo.controller;

import com.pozafly.tripllo.model.network.Message;
import com.pozafly.tripllo.model.network.ResponseMessage;
import com.pozafly.tripllo.model.network.StatusEnum;
import com.pozafly.tripllo.model.request.UserApiRequest;
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
@RequestMapping("/user")
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

    @GetMapping("/valid/{id}")
    public Boolean userIdvValid(@PathVariable String id) {
        return userService.userIdValid(id);
    }

    @PostMapping("")
    public ResponseEntity<Message> createUser(@RequestBody UserApiRequest request) {
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

    @PutMapping("")
    public ResponseEntity<Message> updateUser(@RequestBody UserApiRequest request) {
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

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable String id) {
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
