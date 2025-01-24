package com.aqryuz.backend.workspace.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.chat.controller.payload.Message;
import com.aqryuz.backend.workspace.model.WorkspaceMessage;
import com.aqryuz.backend.workspace.service.WorkspaceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorkspaceChatController {

  private final WorkspaceMessageService workspaceMessageService;

  @MessageMapping("/workspaces/{workspaceId}")
  @SendTo("/topic/workspaces/{workspaceId}")
  public MessageResponse sendMessage(
      @DestinationVariable Long workspaceId, Message message, @AuthenticationPrincipal User user) {

    WorkspaceMessage newMessage =
        workspaceMessageService.createAndSaveMessage(workspaceId, message.content(), user);

    return new MessageResponse(
        newMessage.getId(),
        newMessage.getWorkspace().getId(),
        newMessage.getSender().getId(),
        newMessage.getSender().getUsername(),
        newMessage.getContent(),
        newMessage.getTimestamp().toEpochMilli());
  }

  public record MessageResponse(
      Long messageId,
      Long workspaceId,
      Long senderId,
      String sender,
      String content,
      long timestamp) {}
}
