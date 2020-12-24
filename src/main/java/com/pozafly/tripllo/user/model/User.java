package com.pozafly.tripllo.user.model;

import com.pozafly.tripllo.common.domain.network.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity {

    private String id;
    private String password;
    private String email;
    private String name;
    private String picture;
    private String socialYn;
    private String role;

}