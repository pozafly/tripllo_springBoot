package com.pozafly.tripllo.board.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Board extends BaseEntity {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "pain103")
    private String userId;
    @ApiModelProperty(example = "new Board")
    private String title;
    @ApiModelProperty(example = "rgb(0, 121, 191)")
    private String bgColor;
    @ApiModelProperty(example = "초대된 유저들...")
    private String invitedUser;

}
