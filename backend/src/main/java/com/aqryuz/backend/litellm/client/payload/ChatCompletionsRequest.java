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

  public ChatCompletionsRequest(String model, List<ChatMessage> messages) {
    this(model, messages, null, null, null, null, null, null);
  }

}

record ChatMessage(String role, String content) {
}