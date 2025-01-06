package com.aqryuz.backend.authentication.exception;

public enum ErrorCode {
  INVALID_INPUT("Invalid input provided."),
  DUPLICATE_USERNAME("Username already exists."),
  INVALID_CREDENTIALS("Invalid username or password."),
  INTERNAL_SERVER_ERROR("An unexpected error occurred."),
  USER_NOT_FOUND("User not found.");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
