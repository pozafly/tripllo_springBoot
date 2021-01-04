package com.pozafly.tripllo.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChecklistItem {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long checklistId;
    @ApiModelProperty(example = "체크리스트")
    private String item;
    @ApiModelProperty(example = "Y")
    private String isChecked;
}
