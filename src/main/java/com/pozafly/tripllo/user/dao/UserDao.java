package com.pozafly.tripllo.user.dao;

import com.pozafly.tripllo.user.model.User;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import com.pozafly.tripllo.user.model.response.UserApiResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    public User readUser(String id);
    public List<UserApiResponse> readInviteUser(String id);
    public void createUser(UserApiRequest request);
    public void updateUser(Map<String, Object> userInfo);
    public void deleteUser(Map<String, String> userInfo);
}
