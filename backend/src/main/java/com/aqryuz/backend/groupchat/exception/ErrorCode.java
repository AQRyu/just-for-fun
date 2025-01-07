package com.aqryuz.backend.groupchat.exception;

public enum ErrorCode {
  INVALID_INPUT("Invalid input provided."),
  INVALID_GROUP_CREATION("Invalid group creation request");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
