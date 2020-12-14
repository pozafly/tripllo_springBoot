package com.pozafly.tripllo.dao;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.request.UserApiRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User readUser(String id);
    public void createUser(UserApiRequest request);
    public void updateUser(UserApiRequest request);
    public void deleteUser(String id);
}
