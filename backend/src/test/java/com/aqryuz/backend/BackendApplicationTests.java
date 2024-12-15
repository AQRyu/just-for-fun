package com.aqryuz.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.aqryuz.backend.litellm.config.LiteLLMProperties;

@SpringBootTest
@EnableConfigurationProperties(LiteLLMProperties.class) // Add this line

class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
