package com.aqryuz.backend.backendexceptions;

import com.aqryuz.backend.authentication.exception.ErrorCode;
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

  public ApiException(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
    this(errorCode.name(), errorCode.getMessage(), httpStatusCode);
  }

  public ApiException(String errorCode, String message) {
    this(errorCode, message, HttpStatusCode.valueOf(500));
  }

  public ApiException(ErrorCode errorCode) {
    this(errorCode.name(), errorCode.getMessage());
  }
}
