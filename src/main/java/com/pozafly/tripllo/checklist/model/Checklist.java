package com.pozafly.tripllo.checklist.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Checklist extends BaseEntity {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long cardId;
    @ApiModelProperty(example = "체크리스트")
    private String title;
}
