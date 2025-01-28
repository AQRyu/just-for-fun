package com.aqryuz.backend.authentication.controller.payload;

import com.aqryuz.backend.authentication.model.Role;

public record LoginResponse(String jwt, Long id, String username, Role role) {}
