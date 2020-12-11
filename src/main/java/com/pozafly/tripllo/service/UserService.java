package com.pozafly.tripllo.service;

import com.pozafly.tripllo.model.request.UserApiRequest;
import com.pozafly.tripllo.model.response.UserApiResponse;

public interface UserService {
    public UserApiResponse getUser(String id);
    public Boolean userIdValid(String id);
    public UserApiResponse createUser(UserApiRequest request);
}
