package com.pozafly.tripllo.board.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Board extends BaseEntity {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;

}
