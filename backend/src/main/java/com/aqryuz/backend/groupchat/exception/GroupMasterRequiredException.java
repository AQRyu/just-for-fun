package com.aqryuz.backend.groupchat.exception;

public class GroupMasterRequiredException extends RuntimeException {
  public GroupMasterRequiredException(String message) {
    super(message);
  }
}
