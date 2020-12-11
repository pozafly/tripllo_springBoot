package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.network.Header;
import com.pozafly.tripllo.model.response.UserApiResponse;
import com.pozafly.tripllo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Header<UserApiResponse> getUser(String id) {
        User user = userDao.getUser(id);
        UserApiResponse newUser = UserApiResponse.builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .name(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
        System.out.println("@@@@@@@@@@");
        System.out.println(user);
        return Header.OK(newUser);
    }
}
