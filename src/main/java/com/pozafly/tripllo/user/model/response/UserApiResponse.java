package com.pozafly.tripllo.user.model.response;

import com.pozafly.tripllo.user.model.User;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApiResponse {

    private String id;
    private String email;
    private String name;
    private String picture;
    private String social;
    private String bio;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public UserApiResponse(User user) {
        if(!ObjectUtils.isEmpty(user)) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.picture = user.getPicture();
            this.social = user.getSocial();
            this.createdAt = user.getCreatedAt();
            this.createdBy = user.getCreatedBy();
            this.updatedAt = user.getUpdatedAt();
            this.updatedBy = user.getUpdatedBy();
        }
    }
}
