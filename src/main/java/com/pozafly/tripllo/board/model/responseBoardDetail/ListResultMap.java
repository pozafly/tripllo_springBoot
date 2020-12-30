package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class ListResultMap {

    private Long id;
    private Long boardId;
    private String title;
    private Double pos;

    private List<CardResultMap> cards;
}
