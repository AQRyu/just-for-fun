package com.aqryuz.backend.chat.controller.payload;

import java.util.List;

import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest.ChatMessage;

public record ZeroShotRequest(List<ChatMessage> messages) {
}
