package com.pozafly.tripllo.dao;

import com.pozafly.tripllo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDAO {

    @Select("select * from testuser")
    public User getUser();
}
