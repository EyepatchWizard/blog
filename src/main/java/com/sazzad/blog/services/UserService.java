package com.sazzad.blog.services;

import com.sazzad.blog.domain.dtos.RegistrationRequest;
import com.sazzad.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
    Void registration(RegistrationRequest request);
}
