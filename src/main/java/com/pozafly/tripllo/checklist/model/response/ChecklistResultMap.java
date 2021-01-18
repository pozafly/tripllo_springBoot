package com.pozafly.tripllo.checklist.model.response;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ChecklistResultMap extends BaseEntity {

    private Long id;
    private Long cardId;
    private String title;
    private List<ChecklistItemResultMap> items;

}
