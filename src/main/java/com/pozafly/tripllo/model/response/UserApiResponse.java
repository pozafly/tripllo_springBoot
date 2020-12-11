package com.pozafly.tripllo.model.response;

import com.pozafly.tripllo.model.User;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserApiResponse {

    private String id;
    private String password;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserApiResponse(User user) {
        if(!ObjectUtils.isEmpty(user)) {
            this.id = user.getId();
            this.password = user.getPassword();
            this.email = user.getEmail();
            this.name = user.getName();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }
    }
}
