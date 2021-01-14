package com.pozafly.tripllo.common.interceptor;

import java.nio.file.AccessDeniedException;

public class AuthorizationException extends AccessDeniedException {
    private static final long serialVersionUID = 1L;

    public AuthorizationException() {
        super("인증 오류");
    }

}
