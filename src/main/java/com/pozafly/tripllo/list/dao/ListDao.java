package com.pozafly.tripllo.list.dao;

import com.pozafly.tripllo.list.model.Lists;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ListDao {
    public void createList(Map<String, Object> listInfo);
    public void updateList(Map<String, Object> listInfo);
    public void deleteList(Long listId);
}
