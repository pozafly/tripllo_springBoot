package com.pozafly.tripllo.boardHasLike.model;

import lombok.Data;

@Data
public class BoardHasLike {

    private Long id;
    private Long boardId;
    private String userId;
    private int likeCount;

}
