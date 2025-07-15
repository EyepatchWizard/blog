package com.sazzad.blog.services;

import com.sazzad.blog.domain.dtos.AuthResponse;
import com.sazzad.blog.domain.dtos.RegistrationRequest;
import com.sazzad.blog.domain.dtos.RegistrationResponse;
import com.sazzad.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
    AuthResponse registerUser(RegistrationRequest request);
}
