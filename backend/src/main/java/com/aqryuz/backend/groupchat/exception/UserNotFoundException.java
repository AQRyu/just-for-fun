package com.aqryuz.backend.groupchat.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
  public UserNotFoundException() {
    super(
        ErrorCode.USER_NOT_FOUND.name(),
        ErrorCode.USER_NOT_FOUND.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
