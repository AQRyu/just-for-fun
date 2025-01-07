package com.aqryuz.backend.groupchat.controller.payload;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record GroupChatCreationRequest(
    @NotBlank(message = "Group name cannot be blank") String groupName, Set<Long> memberIds) {}
