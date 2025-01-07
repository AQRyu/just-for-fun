package com.aqryuz.backend.groupchat.exception;

public record ApiResponse<T>(ErrorCode errorCode, String message, T data) {

  public static <T> ApiResponse<T> create(ErrorCode errorCode) {
    return new ApiResponse<>(errorCode, errorCode.getMessage(), null);
  }
}
