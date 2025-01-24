package com.aqryuz.backend.workspace.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.controller.payload.WorkspaceCreationRequest;
import com.aqryuz.backend.workspace.model.Workspace;
import com.aqryuz.backend.workspace.service.WorkspaceCreationService;
import com.aqryuz.backend.workspace.service.WorkspaceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

  private final WorkspaceCreationService workspaceCreationService;
  private final WorkspaceService workspaceService;

  @GetMapping
  public List<Workspace> get(@AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    return workspaceService.getAllWorkspaces(user);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Workspace create(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody @Valid WorkspaceCreationRequest request) {
    User user = (User) userDetails;
    return workspaceCreationService.create(request, user.getId());
  }
}
