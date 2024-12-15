package com.aqryuz.backend.litellm.client.payload;

import java.util.List;

public record ChatCompletionsRequest(
    String model,
    List<ChatMessage> messages,
    Double temperature,
    Integer max_tokens,
    Double top_p,
    Double frequency_penalty,
    Double presence_penalty,
    List<String> stop) {

  public ChatCompletionsRequest(List<ChatMessage> messages) {
    this("gemini-1.5-pro", messages, null, null, null, null, null, null);
  }

  public record ChatMessage(String role, String content) {
  }

}