package com.aqryuz.backend.groupchat.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends ApiException {
  public GroupNotFoundException() {
    super(
        ErrorCode.GROUP_NOT_FOUND.name(),
        ErrorCode.GROUP_NOT_FOUND.getMessage(),
        HttpStatus.NOT_FOUND);
  }
}
