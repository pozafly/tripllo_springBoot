package com.pozafly.tripllo.list.dao;

import com.pozafly.tripllo.list.model.Lists;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListDao {
    public void createList(Lists board);
    public void updateList(Lists board);
    public void deleteList(Long listId);
}
