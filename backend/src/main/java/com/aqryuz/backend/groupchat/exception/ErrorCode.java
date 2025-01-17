package com.aqryuz.backend.groupchat.exception;

public enum ErrorCode {
  GROUP_MASTER_REQUIRED("group.master.required"),
  INVALID_GROUP_CREATION("invalid.group.creation"),
  RESOURCE_NOT_FOUND("resource.not.found");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
