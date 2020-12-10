package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUser(String userId) {
        return userDao.getUser(userId);
    }
}
