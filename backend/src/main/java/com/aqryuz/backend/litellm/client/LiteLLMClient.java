package com.aqryuz.backend.litellm.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.aqryuz.backend.litellm.config.LiteLLMProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiteLLMClient {

  private final RestClient restClient;

  public LiteLLMClient(LiteLLMProperties liteLLMProperties) {
    this.restClient = RestClient
        .builder()
        .baseUrl(liteLLMProperties.getUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("Authorization", "Bearer " + liteLLMProperties.getKey())
        .build();
  }

}
