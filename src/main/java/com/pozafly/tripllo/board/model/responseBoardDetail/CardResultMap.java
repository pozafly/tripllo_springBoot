package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;
import lombok.Getter;

@Getter
public class CardResultMap {

    private Long id;
    private Long listId;
    private String title;
    private Double pos;
    private String description;
    private String labelColor;
    private String location;
}
