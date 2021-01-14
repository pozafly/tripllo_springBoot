package com.pozafly.tripllo.checklist.model.response;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class ChecklistResultMap extends BaseEntity {

    private Long id;
    private Long cardId;
    private String title;
    private List<ChecklistItemResultMap> items;

}
