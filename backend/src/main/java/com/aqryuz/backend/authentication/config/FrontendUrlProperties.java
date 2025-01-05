package com.aqryuz.backend.authentication.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "frontend")
@Validated
@Getter
@Setter
public class FrontendUrlProperties {

  @NotEmpty private String url;
}
