package com.aqryuz.backend.backendexceptions.config;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.aqryuz.backend.authentication.exception.DuplicateUsernameException;
import com.aqryuz.backend.backendexceptions.ApiException;
import com.aqryuz.backend.backendexceptions.dto.ApiErrorResponse;
import com.aqryuz.backend.backendexceptions.dto.FieldError;
import com.aqryuz.backend.backendexceptions.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

  private final MessageService messageService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {

    Locale locale = request.getLocale();

    List<FieldError> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                error ->
                    new FieldError(
                        error.getField(),
                        messageService.getMessage(
                            error.getDefaultMessage(), locale, error.getArguments())))
            .toList();

    String message = messageService.getMessage("validation.error", locale);

    ApiErrorResponse errorResponse =
        new ApiErrorResponse(HttpStatus.BAD_REQUEST.name(), message, fieldErrors);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> handleDuplicateUsername(DuplicateUsernameException ex, Locale locale) {
    String code = ex.getErrorCode();
    String message = messageService.getMessage(ex.getMessage(), locale);
    log.warn(message);
    ApiErrorResponse response = new ApiErrorResponse(code, message);
    return new ResponseEntity<>(response, ex.getHttpStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, Locale locale) {
    String message = messageService.getMessage("generic.error", locale);

    ApiErrorResponse errorResponse =
        new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), message);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
