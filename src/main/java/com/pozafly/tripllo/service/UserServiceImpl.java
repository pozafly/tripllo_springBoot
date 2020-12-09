package com.pozafly.tripllo.service;

import com.pozafly.tripllo.dao.UserDAO;
import com.pozafly.tripllo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public User getUser() {

        return userDAO.getUser();
    }
}
