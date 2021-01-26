package com.pozafly.tripllo.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String userId;
    private String userEmail;
    private String newPassword;
    private String emailTitle;
    private String content;
}
