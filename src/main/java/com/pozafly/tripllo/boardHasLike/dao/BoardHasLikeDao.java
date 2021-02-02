package com.pozafly.tripllo.boardHasLike.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BoardHasLikeDao {
    public void createBoardHasLike(Map<String, Object> info);
    public void deleteBoardHasLike(Map<String, Object> info);
    public void updateBoardLikeCount(Map<String, Object> info);
}
