package com.pozafly.tripllo.boardHasHashtag.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BoardHasHashtagDao {

    public void createBoardHasHashtag(Map<String, Long> info);
    public void deleteBoardHasHashtag(Map<String, Long> info);

}
