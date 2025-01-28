package com.aqryuz.backend.chat.controller;

import com.aqryuz.backend.chat.controller.payload.Message;
import java.time.Instant;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

  @MessageMapping("/sendMessage")
  @SendTo("/topic/newMessage")
  public Message sendMessage(Message message) {
    return new Message(message.sender(), message.content(), Instant.now());
  }
}
