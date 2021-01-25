package com.pozafly.tripllo.fileUpload.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Files {

    private Long id;
    private Long cardId;
    private String fileName;
    private String extension;
    private String link;
    private LocalDateTime createdAt;
    private String createdBy;

}
