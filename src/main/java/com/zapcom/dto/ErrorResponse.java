package com.zapcom.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Rama Gopal
 * Project Name - auth-service
 */
public record ErrorResponse(
        int status,
        String error,
        List<String> messages,
        LocalDateTime timestamp
) {}

