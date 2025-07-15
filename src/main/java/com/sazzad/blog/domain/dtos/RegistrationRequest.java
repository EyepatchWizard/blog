package com.sazzad.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "Must have a name")
    @Size(min = 2, max = 30, message = "Name must be between {min} and {max} character")
    private String name;

    @NotBlank(message = "You must provide a valid email address")
    private String email;

    @NotBlank(message = "You have to set a password")
    @Size(min = 8, message = "Password must contain at least {min} characters")
    private String password;

}
