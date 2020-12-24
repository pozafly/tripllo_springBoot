package com.pozafly.tripllo.common.domain.network;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @ApiModelProperty(example = "now()")
    private LocalDateTime createdAt;
    @ApiModelProperty(example = "생성자id")
    private String createdBy;
    @ApiModelProperty(example = "now()")
    private LocalDateTime updatedAt;
    @ApiModelProperty(example = "수정자id")
    private String updatedBy;
}
