package com.aqryuz.backend.backendexceptions.config;

public enum ErrorCode {
  VALIDATION_ERROR("validation.error"),
  GENERIC_ERROR("generic.error");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
