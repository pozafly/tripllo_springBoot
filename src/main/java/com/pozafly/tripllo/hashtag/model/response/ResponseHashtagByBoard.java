package com.pozafly.tripllo.hashtag.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseHashtagByBoard {

    private Long id;
    private String title;
    private String bgColor;
    private String publicYn;
    private String hashtag;
    private int likeCount;
    private LocalDateTime createdAt;
    private String createdBy;
    private int ownLike;
    private String createdUserPicture;

}
