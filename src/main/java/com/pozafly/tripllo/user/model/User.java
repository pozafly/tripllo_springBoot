package com.pozafly.tripllo.user.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    private String id;
    private String password;
    private String email;
    private String name;
    private String picture;
    private String social;
    private String bio;
    private String recentBoard;
    private String invitedBoard;
    private String role;

}
