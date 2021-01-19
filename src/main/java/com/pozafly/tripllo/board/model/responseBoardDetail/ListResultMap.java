package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;

import java.util.List;

@Data
public class ListResultMap {

    private Long id;
    private Long boardId;
    private String title;
    private Double pos;

    private List<CardResultMap> cards;
}
