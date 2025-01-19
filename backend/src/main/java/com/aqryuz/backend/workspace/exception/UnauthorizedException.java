package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
  public UnauthorizedException() {
    super(
        ErrorCode.WORKSPACE_UNAUTHORIZED.name(),
        ErrorCode.WORKSPACE_UNAUTHORIZED.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
