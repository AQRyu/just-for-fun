package com.aqryuz.backend.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aqryuz.backend.chat.controller.payload.ZeroShotRequest;
import com.aqryuz.backend.chat.controller.payload.ZeroShotResponse;
import com.aqryuz.backend.chat.mapper.LiteLLMMapper;
import com.aqryuz.backend.litellm.client.LiteLLMClient;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest;
import com.aqryuz.backend.litellm.client.payload.ChatCompletionsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@RequiredArgsConstructor
public class ZeroShotController {
  private final LiteLLMClient liteLLMClient;
  private final LiteLLMMapper liteLLMMapper;

  @PostMapping("/zero-shot")
  public ResponseEntity<ZeroShotResponse> zeroShot(@RequestBody ZeroShotRequest request) {
    try {
      ChatCompletionsRequest chatRequest = liteLLMMapper.toChatCompletionsRequest(request);
      ChatCompletionsResponse chatResponse = liteLLMClient.zeroShot(chatRequest);
      ZeroShotResponse response = liteLLMMapper.toZeroShotResponse(chatResponse);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error during zero-shot processing: {}", e.getMessage(), e);
      return ResponseEntity.internalServerError().body(new ZeroShotResponse("Error processing request."));
    }
  }
}
