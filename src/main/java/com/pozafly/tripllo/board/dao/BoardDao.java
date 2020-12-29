package com.pozafly.tripllo.board.dao;

import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.responseBoardDetail.ResponseBoardDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    public List<Board> readBoardList(String userId);
    public int boardCount(Long boardId);
    public List<ResponseBoardDetail> readBoardDetail(Long boardId);
    public int createBoard(Map<String, Object> boardInfo);
    public void updateBoard(Map<String, Object> boardInfo);
    public void deleteBoard(Long boardId);
}
