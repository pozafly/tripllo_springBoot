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
    public UserApiResponse readUser(String id) {
        return userDao.readUser(id);
    }

    @Override
    public Boolean userIdValid(String id) {

        UserApiResponse user = userDao.readUser(id);
        if (!ObjectUtils.isEmpty(user)) {
            return !user.getId().equals(id);
        } else {
            return true;
        }
    }

    @Override
    public UserApiResponse createUser(UserApiRequest request) {
        if(userIdValid(request.getId())) {
            userDao.createUser(request);
            return readUser(request.getId());
        } else {
            return null;
        }
    }

    @Override
    public UserApiResponse updateUser(UserApiRequest request) {
        if(!userIdValid(request.getId())) {
            userDao.updateUser(request);
            return readUser(request.getId());
        } else {
            return null;
        }
    }

    @Override
    public UserApiResponse deleteUser(String id) {
        if(!userIdValid(id)) {
            userDao.deleteUser(id);
            return new UserApiResponse(id, null, null, null, null, null);
        } else {
            return null;
        }
    }
}
