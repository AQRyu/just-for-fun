package com.aqryuz.backend.litellm.client.payload;

import java.util.List;

public record ChatCompletionsResponse(String id, List<AIChoice> choices, long created, String model, AIUsage usage) {

  public record AIChoice(String finishReason, int index, AIMessage message) {
  }

  public record AIMessage(String role, String content, Object toolCalls, Object functionCall) {
  }

  public record AIUsage(int promptTokens, int completionTokens, int totalTokens) {
  }
}