package com.sazzad.blog.services.impl;

import com.sazzad.blog.domain.dtos.AuthResponse;
import com.sazzad.blog.domain.dtos.RegistrationRequest;
import com.sazzad.blog.domain.dtos.RegistrationResponse;
import com.sazzad.blog.domain.entities.User;
import com.sazzad.blog.mappers.UserInfoMapper;
import com.sazzad.blog.repositories.UserRepository;
import com.sazzad.blog.services.AuthenticationService;
import com.sazzad.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper userInfoMapper;
    private final AuthenticationService authenticationService;

    @Override
    public User getUserById(UUID id) {

        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id:" + id));
    }

    @Override
    @Transactional
    public AuthResponse registerUser(RegistrationRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(newUser);

        UserDetails userDetails = authenticationService.authenticate(request.getEmail(), request.getPassword());
        String token = authenticationService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(86400)
                .build();

    }
}
