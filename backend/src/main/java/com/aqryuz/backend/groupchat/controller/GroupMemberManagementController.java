package com.aqryuz.backend.groupchat.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.groupchat.service.GroupMemberManagementService;
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
@RequestMapping("/api/groupchat/groups/{groupId}/members")
@RequiredArgsConstructor
@Slf4j
public class GroupMemberManagementController {

  private final GroupMemberManagementService groupMemberManagementService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> getMembersOfGroup(
      @PathVariable Long groupId, @AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    return groupMemberManagementService.getMembersOfGroup(groupId, user);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> addMembersToGroup(
      @PathVariable Long groupId,
      @RequestBody @Valid Set<Long> memberIds,
      @AuthenticationPrincipal UserDetails userDetails) {
    User currentUser = (User) userDetails;
    return groupMemberManagementService.addMembersToGroup(groupId, memberIds, currentUser);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> removeMembersFromGroup(
      @PathVariable Long groupId,
      @RequestBody @Valid Set<Long> memberIds,
      @AuthenticationPrincipal UserDetails userDetails) {
    User currentUser = (User) userDetails;
    return groupMemberManagementService.removeMembersFromGroup(groupId, memberIds, currentUser);
  }
}
