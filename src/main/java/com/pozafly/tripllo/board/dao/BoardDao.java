package com.pozafly.tripllo.board.dao;

import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.responseBoardDetail.BoardResultMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    public Board readBoardOne(Long boardId);
    public List<Board> readBoardList(String userId);
    public int boardCount(Long boardId);
    public List<Board> readRecentBoards(List<String> boardIdList);
    public List<Board> readInvitedBoards(String userId);
    public BoardResultMap readBoardDetail(Long boardId);
    public Long createBoard(Map<String, Object> boardInfo);
    public void updateBoard(Map<String, Object> boardInfo);
    public void deleteBoard(Long boardId);
}
