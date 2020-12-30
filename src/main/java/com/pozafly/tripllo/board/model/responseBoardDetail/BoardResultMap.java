package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResultMap {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;

    private List<ListResultMap> lists;
}
