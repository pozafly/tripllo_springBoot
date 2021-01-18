package com.pozafly.tripllo.checklist.model.response;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ChecklistItemResultMap extends BaseEntity {

    private Long id;
    private Long checklistId;
    private String item;
    private String isChecked;

}
