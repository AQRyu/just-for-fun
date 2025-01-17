package com.aqryuz.backend.backendexceptions.dto;

public enum ErrorCode {
  DUPLICATE_USERNAME("validation.field.username.duplicate"),
  INVALID_CREDENTIALS("validation.field.credentials.invalid");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
