package com.pozafly.tripllo.comment.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Comment extends BaseEntity {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long cardId;
    @ApiModelProperty(example = "pain103")
    private String userId;
    @ApiModelProperty(example = "코멘트....")
    private String comment;
    @ApiModelProperty(example = "대댓글 여부")
    private int dept;
    @ApiModelProperty(example = "댓글 그룹 넘버")
    private Long groupNum;
    @ApiModelProperty(example = "삭제여부")
    private String deleteYn;
    @ApiModelProperty(example = "유저 프로필 이미지")
    private String picture;
}
