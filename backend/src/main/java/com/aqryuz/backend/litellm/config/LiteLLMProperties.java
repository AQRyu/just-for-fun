package com.aqryuz.backend.litellm.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "litellm.api")
@Validated
@Getter
@Setter
public class LiteLLMProperties {

  @NotEmpty private String url;

  @NotEmpty private String key;
}
