package com.aqryuz.backend.groupchat.exception;

public enum ErrorCode {
  INVALID_INPUT("Invalid input provided."),
  INVALID_GROUP_CREATION("Invalid group creation request"),
  RESOURCE_NOT_FOUND("Resource not found"),
  GROUP_MASTER_REQUIRED("Group master required"),
  INTERNAL_SERVER_ERROR("An unexpected error occurred.");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
