package com.pozafly.tripllo.board.dao;

import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.responseBoardDetail.ResponseBoardDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    public List<Board> readBoardList(String userId);
    public int boardCount(Long boardId);
    public List<ResponseBoardDetail> readBoardDetail(Long boardId);
    public void createBoard(Board board);
    public void updateBoard(Board board);
    public void deleteBoard(Long boardId);
}
