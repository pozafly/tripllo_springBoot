package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardResultMap {

    private Long id;
    private Long listId;
    private String title;
    private Double pos;
    private String description;
    private String labelColor;
    private String location;
    private String isChecklist;
    private String isAttachment;
    private LocalDateTime dueDate;
    private int isComment;

}
