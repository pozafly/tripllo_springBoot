package com.pozafly.tripllo.checklist.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ChecklistResultMap {

    private Long id;
    private Long cardId;
    private String title;
    private List<ChecklistItemResultMap> items;

}
