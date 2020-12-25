package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;

import java.util.List;

@Data
public class BoardResultMap {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;

    private List<ListResultMap> lists;
}
