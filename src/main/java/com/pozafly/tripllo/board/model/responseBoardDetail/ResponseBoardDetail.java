package com.pozafly.tripllo.board.model.responseBoardDetail;

import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.list.model.Lists;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseBoardDetail {

    private Long boardId;
    private String boardTitle;
    private String boardBgColor;
    private List<Lists> lists;

    private Long listId;
    private Long listBoardId;
    private String listTitle;
    private String listPos;
    private List<Card> cards;
    
    private Long cardId;
    private Long cardListId;
    private String cardTitle;
    private String cardPos;
    private String cardDescription;
    private String cardLabelColor;
    private String cardLocation;
    private String cardIsChecklist;
    private LocalDateTime cardDueDate;

}
