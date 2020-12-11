package com.pozafly.tripllo.service.impl;

import com.pozafly.tripllo.dao.UserDao;
import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.request.UserApiRequest;
import com.pozafly.tripllo.model.response.UserApiResponse;
import com.pozafly.tripllo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public UserApiResponse getUser(String id) {
        User user = userDao.getUser(id);
        return new UserApiResponse(user);
    }

    @Override
    public Boolean userIdValid(String id) {

        User user = userDao.getUser(id);
        if (!ObjectUtils.isEmpty(user)) {
            return !user.getId().equals(id);
        } else {
            return true;
        }
    }

    @Transactional
    @Override
    public UserApiResponse createUser(UserApiRequest request) {
        userDao.createUser(request);
        return new UserApiResponse(request.getId(),null, request.getEmail(), request.getName(), null, null);
    }
}
