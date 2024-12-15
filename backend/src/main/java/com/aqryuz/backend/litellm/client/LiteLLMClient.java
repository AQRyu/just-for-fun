package com.aqryuz.backend.litellm.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsResponse;
import com.aqryuz.backend.litellm.config.LiteLLMProperties;
import com.aqryuz.backend.litellm.exception.LiteLLMClientException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiteLLMClient {

  private final RestClient restClient;

  public LiteLLMClient(LiteLLMProperties liteLLMProperties, RestClient.Builder restClientBuilder) {
    this.restClient = restClientBuilder
        .baseUrl(liteLLMProperties.getUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("Authorization", "Bearer " + liteLLMProperties.getKey())
        .build();
  }

  public ChatCompletionsResponse zeroShot(ChatCompletionsRequest chatCompletionsRequest) {

    try {
      return restClient.post()
          .uri("/chat/completions")
          .body(chatCompletionsRequest)
          .retrieve()
          .body(ChatCompletionsResponse.class);
    } catch (HttpClientErrorException ex) {
      if (ex.getStatusCode().is4xxClientError()) {
        String errorMessage = String.format("Client error calling LiteLLM API: %s - %s", ex.getStatusCode(),
            ex.getResponseBodyAsString());
        log.error(errorMessage, ex);
        throw new LiteLLMClientException(errorMessage, ex);
      } else {
        throw ex;
      }

    } catch (RestClientResponseException ex) {
      String errorMessage = String.format("Error calling LiteLLM API: %s - %s", ex.getStatusCode().value(),
          ex.getResponseBodyAsString());
      log.error(errorMessage, ex);
      throw new LiteLLMClientException(errorMessage, ex);

    } catch (Exception ex) {
      String errorMessage = "Unexpected error calling LiteLLM API";
      log.error(errorMessage, ex);
      throw new LiteLLMClientException(errorMessage, ex);
    }

  }
}
