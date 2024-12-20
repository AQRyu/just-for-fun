package com.aqryuz.backend.authentication.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
@Getter
@Setter
public class JWTProperties {

  @NotEmpty private int expirationMs;

  @NotEmpty private String secret;
}
