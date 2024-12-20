package com.aqryuz.backend.litellm.client.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ChatCompletionsResponse(
    String id, List<AIChoice> choices, long created, String model, AIUsage usage) {

  public record AIChoice(
      @JsonProperty("finish_reason") String finishReason, int index, AIMessage message) {}

  public record AIMessage(
      String role,
      String content,
      @JsonProperty("tool_calls") Object toolCalls,
      @JsonProperty("function_call") Object functionCall) {}

  public record AIUsage(
      @JsonProperty("prompt_tokens") int promptTokens,
      @JsonProperty("completion_tokens") int completionTokens,
      @JsonProperty("total_tokens") int totalTokens) {}
}
