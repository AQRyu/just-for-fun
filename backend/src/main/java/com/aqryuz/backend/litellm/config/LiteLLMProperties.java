package com.aqryuz.backend.litellm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "litellm.api")
@Validated
@Getter
@Setter
public class LiteLLMProperties {

  @NotEmpty
  private String url;

  @NotEmpty
  private String key;

}
