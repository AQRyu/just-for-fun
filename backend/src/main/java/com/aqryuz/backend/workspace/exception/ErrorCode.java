package com.aqryuz.backend.workspace.exception;

public enum ErrorCode {
  WORKSPACE_MASTER_REQUIRED("workspace.master.required"),
  INVALID_WORKSPACE_CREATION("invalid.workspace.creation"),
  WORKSPACE_NOT_FOUND("workspace.not.found"),
  USER_NOT_FOUND("user.not.found"),
  WORKSPACE_UNAUTHORIZED("workspace.unauthorized"),
  RESOURCE_NOT_FOUND("resource.not.found");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
