package com.aqryuz.backend.groupchat.exception;

public class InvalidGroupCreationException extends RuntimeException {
  public InvalidGroupCreationException(String message) {
    super(message);
  }
}
