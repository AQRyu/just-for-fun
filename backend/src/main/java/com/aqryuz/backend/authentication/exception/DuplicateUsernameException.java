package com.aqryuz.backend.authentication.exception;

import org.springframework.http.HttpStatus;

import com.aqryuz.backend.backendexceptions.ApiException;

public class DuplicateUsernameException extends ApiException {

  public DuplicateUsernameException() {
    super(ErrorCode.DUPLICATE_USERNAME, HttpStatus.CONFLICT);
  }
}
