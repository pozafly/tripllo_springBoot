package com.pozafly.tripllo.user.dao;

import com.pozafly.tripllo.user.model.User;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao {
    public User readUser(String id);
    public void createUser(UserApiRequest request);
    public void updateUser(Map<String, Object> userInfo);
    public void deleteUser(Map<String, String> userInfo);
}
