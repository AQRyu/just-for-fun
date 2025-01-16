package com.aqryuz.backend.backendexceptions.dto;

import lombok.Data;

@Data
public class FieldError {

  private String field;
  private String message;

  public FieldError(org.springframework.validation.FieldError error) {
    this.field = error.getField();
    this.message = error.getDefaultMessage();
  }

  public FieldError(String field, String message) {
    this.field = field;
    this.message = message;
  }
}
