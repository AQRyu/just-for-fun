package com.aqryuz.backend.profile.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserProfileRecord(
    @NotBlank(message = "Nickname cannot be blank")
        @Size(min = 2, max = 50, message = "Nickname must be between 2 and 50 characters")
        String nickName,
    @Email(message = "Invalid email format") String email,
    @Size(max = 255, message = "Bio must be at most 255 characters") String bio,
    String profilePictureURL) {}
