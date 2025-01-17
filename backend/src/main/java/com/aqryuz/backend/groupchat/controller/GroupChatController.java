package com.aqryuz.backend.groupchat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.groupchat.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.groupchat.model.Group;
import com.aqryuz.backend.groupchat.service.GroupCreationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/groupchat")
@RequiredArgsConstructor
@Slf4j
public class GroupChatController {

  private final GroupCreationService groupCreationService;

  @PostMapping("/groups")
  @ResponseStatus(HttpStatus.CREATED)
  public Group createGroup(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody @Valid GroupChatCreationRequest request) {
    User user = (User) userDetails;
    return groupCreationService.createGroup(request, user.getId());
  }
}
