package com.zapcom.dto;

/** Created by Rama Gopal Project Name - auth-service */
public record CustomerResponse(
    String businessEmail, String businessName, String ownerName, String password) {}
