package com.aqryuz.backend.workspace.exception;

public enum ErrorCode {
  GROUP_MASTER_REQUIRED("group.master.required"),
  INVALID_GROUP_CREATION("invalid.group.creation"),
  GROUP_NOT_FOUND("group.not.found"),
  USER_NOT_FOUND("user.not.found"),
  GROUP_UNAUTHORIZED("group.unauthorized"),
  RESOURCE_NOT_FOUND("resource.not.found");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
