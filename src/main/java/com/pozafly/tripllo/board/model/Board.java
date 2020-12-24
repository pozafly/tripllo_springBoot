package com.pozafly.tripllo.board.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;

@Data
public class Board extends BaseEntity {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;

}
