package com.pozafly.tripllo.dao;

import com.pozafly.tripllo.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User getUser(String id);
}
