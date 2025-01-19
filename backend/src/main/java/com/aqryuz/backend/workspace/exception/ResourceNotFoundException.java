package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
  public ResourceNotFoundException() {
    super(
        ErrorCode.RESOURCE_NOT_FOUND.name(),
        ErrorCode.RESOURCE_NOT_FOUND.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
