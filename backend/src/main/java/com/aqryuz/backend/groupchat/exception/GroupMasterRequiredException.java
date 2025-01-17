package com.aqryuz.backend.groupchat.exception;

import com.aqryuz.backend.backendexceptions.ApiException;
import org.springframework.http.HttpStatus;

public class GroupMasterRequiredException extends ApiException {
  public GroupMasterRequiredException() {
    super(
        ErrorCode.GROUP_MASTER_REQUIRED.name(),
        ErrorCode.GROUP_MASTER_REQUIRED.getMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
