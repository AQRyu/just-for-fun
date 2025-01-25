package com.aqryuz.backend.chat.controller.payload;

import java.time.Instant;

public record Message(String sender, String content, Instant timestamp) {}
