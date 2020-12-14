package com.pozafly.tripllo.service;

import com.pozafly.tripllo.model.request.LoginApiRequest;

public interface LoginService {
    public String createToken(LoginApiRequest loginRequest);
}
