package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
  public UnauthorizedException() {
    super(
        ErrorCode.GROUP_UNAUTHORIZED.name(),
        ErrorCode.GROUP_UNAUTHORIZED.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
