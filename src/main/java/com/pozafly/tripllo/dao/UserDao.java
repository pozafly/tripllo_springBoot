package com.pozafly.tripllo.dao;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.request.UserApiRequest;
import com.pozafly.tripllo.model.response.UserApiResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User getUser(String id);
    public void createUser(UserApiRequest request);
}
