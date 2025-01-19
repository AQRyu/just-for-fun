package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class WorkspaceMasterRequiredException extends ApiException {
  public WorkspaceMasterRequiredException() {
    super(
        ErrorCode.WORKSPACE_MASTER_REQUIRED.name(),
        ErrorCode.WORKSPACE_MASTER_REQUIRED.getMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
