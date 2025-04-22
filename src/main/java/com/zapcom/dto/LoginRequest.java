package com.zapcom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Created by Rama Gopal Project Name - auth-service */

public record LoginRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password must not be blank")
        String password
) {}

