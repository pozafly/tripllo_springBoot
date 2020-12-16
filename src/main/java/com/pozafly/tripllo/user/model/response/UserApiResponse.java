package com.pozafly.tripllo.user.model.response;

import com.pozafly.tripllo.user.model.User;
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
    private String picture;
    private String socialYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserApiResponse(User user) {
        if(!ObjectUtils.isEmpty(user)) {
            this.id = user.getId();
            this.password = user.getPassword();
            this.email = user.getEmail();
            this.name = user.getName();
            this.picture = user.getPicture();
            this.socialYn = user.getSocialYn();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }
    }
}
