package com.aqryuz.backend.profile.model;

public record UserProfileRecord(
    Long id, String nickName, String email, String bio, String profilePictureURL) {}
