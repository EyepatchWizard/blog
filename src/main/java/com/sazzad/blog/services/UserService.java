package com.sazzad.blog.services;

import com.sazzad.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
