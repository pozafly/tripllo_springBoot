package com.pozafly.tripllo.service;

import com.pozafly.tripllo.model.User;
import com.pozafly.tripllo.model.network.Header;
import com.pozafly.tripllo.model.response.UserApiResponse;

public interface UserService {
    public Header<UserApiResponse> getUser(String id);
}
