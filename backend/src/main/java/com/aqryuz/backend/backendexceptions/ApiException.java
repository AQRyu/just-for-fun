package com.aqryuz.backend.backendexceptions;

import java.time.ZonedDateTime;

public class ApiException extends RuntimeException {
  private final String errorCode;
  private final String message;
  private final int httpStatus;
  private final ZonedDateTime timestamp;

  public ApiException(String errorCode, String message, int httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = ZonedDateTime.now();
  }

  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public ApiException(String errorCode, String message) {
    this(errorCode, message, 500);
  }
}
