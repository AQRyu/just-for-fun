package com.aqryuz.backend.chat.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aqryuz.backend.chat.controller.payload.ZeroShotRequest;
import com.aqryuz.backend.chat.controller.payload.ZeroShotResponse;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsResponse;

@Mapper(componentModel = "spring")
public interface LiteLLMMapper {
  @Mapping(target = "model", constant = "gemini-1.5-pro")
  @Mapping(target = "temperature", ignore = true)
  @Mapping(target = "max_tokens", ignore = true)
  @Mapping(target = "top_p", ignore = true)
  @Mapping(target = "frequency_penalty", ignore = true)
  @Mapping(target = "presence_penalty", ignore = true)
  @Mapping(target = "stop", ignore = true)
  ChatCompletionsRequest toChatCompletionsRequest(ZeroShotRequest request);

  default ZeroShotResponse toZeroShotResponse(ChatCompletionsResponse response) {
    try {
      return new ZeroShotResponse(response.choices().get(0).message().content());
    } catch (NullPointerException e) {
      return new ZeroShotResponse("No response from the LLM.");
    }
  }

}
