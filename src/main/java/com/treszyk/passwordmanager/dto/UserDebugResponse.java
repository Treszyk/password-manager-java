package com.treszyk.passwordmanager.dto;

public record UserDebugResponse(
        Long id,
        String username,
        String password,
        String masterSalt,
        boolean masterPasswordSet
) {}
