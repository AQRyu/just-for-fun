package com.aqryuz.backend.backendexceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiException extends RuntimeException {
  private final String errorCode;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  public ApiException(String errorCode, String message, HttpStatusCode httpStatusCode) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }

  public ApiException(String errorCode, String message) {
    this(errorCode, message, HttpStatusCode.valueOf(500));
  }
}
