package com.aqryuz.backend.backendexceptions.service;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final MessageSource messageSource;

  public String getMessage(String code, Locale locale) {
    return messageSource.getMessage(code, null, locale);
  }

  public String getMessage(String code, Locale locale, Object... args) {
    return messageSource.getMessage(code, args, locale);
  }
}
