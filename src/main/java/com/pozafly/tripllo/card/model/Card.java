package com.pozafly.tripllo.card.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Card extends BaseEntity {
    private Long id;
    private Long listId;
    private String title;
    private Double pos;
    private String description;
    private String labelColor;
    private String location;
}
