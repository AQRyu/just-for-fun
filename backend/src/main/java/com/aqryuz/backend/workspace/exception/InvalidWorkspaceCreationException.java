package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidWorkspaceCreationException extends ApiException {
  public InvalidWorkspaceCreationException() {
    super(
        ErrorCode.INVALID_WORKSPACE_CREATION.name(),
        ErrorCode.INVALID_WORKSPACE_CREATION.getMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
