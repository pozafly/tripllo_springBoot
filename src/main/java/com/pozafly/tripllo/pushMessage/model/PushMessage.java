package com.pozafly.tripllo.pushMessage.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PushMessage extends BaseEntity {

    private Long id;
    private String senderId;
    private String targetId;
    private String content;
    private Long boardId;
    private String isRead;

}
