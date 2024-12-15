package com.aqryuz.backend.litellm.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import com.aqryuz.backend.BackendApplication;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsResponse;
import com.aqryuz.backend.litellm.config.LiteLLMProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest({ LiteLLMClient.class, BackendApplication.class }) // This annotation is key!
@EnableConfigurationProperties(LiteLLMProperties.class) // Add this line
@TestPropertySource(properties = {
    "litellm.api.url=test",
    "litellm.api.key=sk-*"
})
class LiteLLMClientTest {

  @Autowired
  private LiteLLMClient liteLLMClient;

  @Autowired
  private MockRestServiceServer server;

  @Test
  void testZeroShotWithRestClientTest() throws JsonProcessingException {
    String jsonReq = """
        {
          "messages": [
            {
              "role": "user",
              "content": "What is the capital of France?"
            },
            {
              "role": "assistant",
              "content": "Paris is the capital of France."
            }
          ]
        }
        """;
    var req = new ObjectMapper().readValue(jsonReq, ChatCompletionsRequest.class);

    String jsonRes = """
        {
          "id": "chatcmpl-7t9z8PLwYb7qdzVOn3kGH8tMem1s1",
          "choices": [
            {
              "finish_reason": "stop",
              "index": 0,
              "message": {
                "role": "assistant",
                "content": "Paris is the capital of France.",
                "tool_calls": null,
                "function_call": null
              }
            }
          ],
          "created": 1678886400,
          "model": "gpt-3.5-turbo-0613",
          "usage": {
            "prompt_tokens": 9,
            "completion_tokens": 7,
            "total_tokens": 16
          }
        }
        """;
    var res = new ObjectMapper().readValue(jsonRes, ChatCompletionsResponse.class);

    this.server.expect(once(), requestTo("test/chat/completions"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().json(jsonReq))
        .andRespond(withSuccess(jsonRes, MediaType.APPLICATION_JSON));

    ChatCompletionsResponse actualResponse = liteLLMClient.zeroShot(req);

    assertEquals(res, actualResponse);
    this.server.verify();

  }
}
