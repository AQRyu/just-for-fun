package com.aqryuz.backend.workspace.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.service.WorkspaceMemberManagementService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workspace/{workspaceId}/members")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceMemberManagementController {

  private final WorkspaceMemberManagementService workspaceMemberManagementService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> getMembers(
      @PathVariable Long workspaceId, @AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    return workspaceMemberManagementService.getMembers(workspaceId, user);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> addMembers(
      @PathVariable Long workspaceId,
      @RequestBody @Valid Set<Long> memberIds,
      @AuthenticationPrincipal UserDetails userDetails) {
    User currentUser = (User) userDetails;
    return workspaceMemberManagementService.addMembers(workspaceId, memberIds, currentUser);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> removeMembers(
      @PathVariable Long workspaceId,
      @RequestBody @Valid Set<Long> memberIds,
      @AuthenticationPrincipal UserDetails userDetails) {
    User currentUser = (User) userDetails;
    return workspaceMemberManagementService.removeMembers(workspaceId, memberIds, currentUser);
  }
}
