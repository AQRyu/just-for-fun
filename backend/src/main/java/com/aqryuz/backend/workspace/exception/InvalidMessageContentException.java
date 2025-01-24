package com.aqryuz.backend.workspace.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidMessageContentException extends ApiException {
  public InvalidMessageContentException() {
    super(
        ErrorCode.INVALID_MESSAGE_CONTENT.name(),
        ErrorCode.INVALID_MESSAGE_CONTENT.getMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
