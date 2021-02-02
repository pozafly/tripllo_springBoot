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
    @ApiModelProperty(example = "new Board")
    private String title;
    @ApiModelProperty(example = "rgb(0, 121, 191)")
    private String bgColor;
    @ApiModelProperty(example = "초대된 유저들...")
    private String invitedUser;
    @ApiModelProperty(example = "공개여부")
    private String publicYn;
    @ApiModelProperty(example = "해쉬태그")
    private String hashtag;
    @ApiModelProperty(example = "210")
    private int likeCount;
    @ApiModelProperty(example = "0: 좋아요 누르지 않음, 1: 좋아요 누름")
    private int ownLike;

}
