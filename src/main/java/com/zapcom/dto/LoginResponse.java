package com.zapcom.dto;

/** Created by Rama Gopal Project Name - auth-service */
public record LoginResponse(String customerEmail, String customerJwtToken, String message) {}
