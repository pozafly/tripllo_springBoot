package com.pozafly.tripllo.service;

import com.pozafly.tripllo.model.response.UserApiResponse;

public interface UserService {
    public UserApiResponse getUser(String id);
}
