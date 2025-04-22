package com.zapcom.dto;

import lombok.Builder;

/** Created by Rama Gopal Project Name - auth-service */
@Builder
public record Response(String message, String activationLink) {}
