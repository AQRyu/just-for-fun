package com.aqryuz.backend.authentication.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends ApiException {

  public DuplicateUsernameException() {
    super(
        ErrorCode.DUPLICATE_USERNAME.name(),
        ErrorCode.DUPLICATE_USERNAME.getMessage(),
        HttpStatus.CONFLICT);
  }
}
