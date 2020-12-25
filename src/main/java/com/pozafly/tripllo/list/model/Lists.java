package com.pozafly.tripllo.list.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Lists extends BaseEntity {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long boardId;
    @ApiModelProperty(example = "갑니다. 여행")
    private String title;
    @ApiModelProperty(example = "1")
    private Double pos;

}
