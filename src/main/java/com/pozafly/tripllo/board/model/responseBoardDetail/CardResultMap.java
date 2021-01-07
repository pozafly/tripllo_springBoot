package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResultMap {

    private Long id;
    private Long listId;
    private String title;
    private Double pos;
    private String description;
    private String labelColor;
    private String location;
    private String isChecklist;
    private LocalDateTime dueDate;
}
