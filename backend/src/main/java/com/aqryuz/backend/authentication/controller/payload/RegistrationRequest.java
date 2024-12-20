package com.aqryuz.backend.authentication.controller.payload;

public record RegistrationRequest(String username, String password, String email) {}
