package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;




}
