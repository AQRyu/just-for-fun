package com.aqryuz.backend.chat.controller.payload;

import com.aqryuz.backend.litellm.client.payload.ChatCompletionsRequest.ChatMessage;
import java.util.List;

public record ZeroShotRequest(List<ChatMessage> messages) {}
