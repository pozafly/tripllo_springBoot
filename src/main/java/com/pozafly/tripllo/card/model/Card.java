package com.pozafly.tripllo.card.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Card extends BaseEntity {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long listId;
    @ApiModelProperty(example = "여행가자~")
    private String title;
    @ApiModelProperty(example = "1")
    private Double pos;
    @ApiModelProperty(example = "제주도 여행을 떠납니다.")
    private String description;
    @ApiModelProperty(example = "rgba(0,0,0,0.3)")
    private String labelColor;
    @ApiModelProperty(example = "대한민국")
    private String location;
    @ApiModelProperty(example = "날짜 formatting")
    private String dueDate;

}
