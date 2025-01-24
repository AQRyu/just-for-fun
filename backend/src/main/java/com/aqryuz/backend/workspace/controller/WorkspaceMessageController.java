package com.aqryuz.backend.workspace.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.model.WorkspaceMessage;
import com.aqryuz.backend.workspace.service.WorkspaceMessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceMessageController {

  private final WorkspaceMessageService workspaceMessageService;

  @GetMapping("/{workspaceId}/messages")
  public List<WorkspaceMessage> getMessages(
      @PathVariable Long workspaceId, @AuthenticationPrincipal User user) {

    return workspaceMessageService.getMessages(workspaceId, user);
  }
}
