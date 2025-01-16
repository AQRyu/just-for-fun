package com.aqryuz.backend.backendexceptions.dto;

import java.util.List;
import lombok.Data;

@Data
public class ApiErrorResponse {
  private String code;
  private String message;
  private List<FieldError> errorDetails;

  public ApiErrorResponse(String code, String name, List<FieldError> errorDetails) {
    this.code = code;
    this.message = name;
    this.errorDetails = errorDetails;
  }

  public ApiErrorResponse(String code, String name) {
    this.code = code;
    this.message = name;
  }
}
