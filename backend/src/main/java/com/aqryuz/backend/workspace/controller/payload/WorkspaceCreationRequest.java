package com.aqryuz.backend.workspace.controller.payload;

import jakarta.validation.constraints.NotBlank;

public record WorkspaceCreationRequest(
    @NotBlank(message = "Workspace name cannot be blank") String name) {}
