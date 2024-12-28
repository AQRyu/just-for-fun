package com.aqryuz.backend.chat.controller.payload;

public record Message(String sender, String content, long timestamp) {}
