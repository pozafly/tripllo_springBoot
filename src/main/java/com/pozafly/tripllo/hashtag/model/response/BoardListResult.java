package com.pozafly.tripllo.hashtag.model.response;

import lombok.Data;

@Data
public class BoardListResult {

    private Long id;
    private String userId;
    private String title;
    private String bgColor;
    private String publicYn;
    private String hashtag;

}
