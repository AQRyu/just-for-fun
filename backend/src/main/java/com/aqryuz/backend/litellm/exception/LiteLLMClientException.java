package com.aqryuz.backend.litellm.exception;

public class LiteLLMClientException extends RuntimeException {
  public LiteLLMClientException(String message, Throwable cause) {
    super(message, cause);
  }
}