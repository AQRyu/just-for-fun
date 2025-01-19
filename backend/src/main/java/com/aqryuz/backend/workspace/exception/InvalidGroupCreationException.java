package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidGroupCreationException extends ApiException {
  public InvalidGroupCreationException() {
    super(
        ErrorCode.INVALID_GROUP_CREATION.name(),
        ErrorCode.INVALID_GROUP_CREATION.getMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
