package com.sazzad.blog.controllers;

import com.sazzad.blog.domain.dtos.AuthResponse;
import com.sazzad.blog.domain.dtos.RegistrationRequest;
import com.sazzad.blog.domain.dtos.RegistrationResponse;
import com.sazzad.blog.services.AuthenticationService;
import com.sazzad.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<AuthResponse> registration(@RequestBody RegistrationRequest request) {

        AuthResponse authResponse = userService.registerUser(request);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
