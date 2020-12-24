package com.pozafly.tripllo.list.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Lists extends BaseEntity {
    private Long id;
    private Long boardId;
    private String title;
    private Double pos;
    private String userId;
}
