package com.pozafly.tripllo.board.model.response;

import lombok.Data;

@Data
public class BoardApiDetailResponse {

    private Long boardId;
    private String boardTitle;
    private String boardBgColor;
    private Long listId;
    private Long listBoardId;
    private String listTitle;
    private String listPos;
    private Long cardId;
    private Long cardListId;
    private String cardTitle;
    private String cardPos;
    private String cardDescription;
    private String cardLabelColor;
    private String location;

}
