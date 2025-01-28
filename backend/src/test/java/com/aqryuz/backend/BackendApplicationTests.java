package com.aqryuz.backend;

import com.aqryuz.backend.litellm.config.LiteLLMProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(LiteLLMProperties.class)
class BackendApplicationTests {}
