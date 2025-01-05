package com.aqryuz.backend.profile.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long userId) {
    super("User not found for user ID: " + userId);
  }
}
