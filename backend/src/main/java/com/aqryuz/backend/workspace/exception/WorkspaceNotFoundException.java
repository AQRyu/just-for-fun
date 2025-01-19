package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class WorkspaceNotFoundException extends ApiException {
  public WorkspaceNotFoundException() {
    super(
        ErrorCode.WORKSPACE_NOT_FOUND.name(),
        ErrorCode.WORKSPACE_NOT_FOUND.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
