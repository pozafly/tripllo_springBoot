package com.pozafly.tripllo.board.model.responseBoardDetail;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardResultMap {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;
    private String invitedUser;
    private LocalDateTime createdAt;
    private String createdBy;
    private String createdByPicture;

    private List<ListResultMap> lists;
}
