package com.aqryuz.backend.authentication.dto;

public record RegistrationRequest(String username, String password, String email) {
}
