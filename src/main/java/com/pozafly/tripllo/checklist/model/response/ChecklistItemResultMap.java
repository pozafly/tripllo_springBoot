package com.pozafly.tripllo.checklist.model.response;

import lombok.Data;

@Data
public class ChecklistItemResultMap {

    private Long id;
    private Long checklistId;
    private String item;
    private String isChecked;

}
