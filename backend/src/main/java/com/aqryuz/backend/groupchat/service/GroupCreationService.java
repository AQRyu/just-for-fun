package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository; // For fetching User details
import com.aqryuz.backend.groupchat.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.groupchat.exception.InvalidGroupCreationException;
import com.aqryuz.backend.groupchat.model.Group; // Existing Group model
import com.aqryuz.backend.groupchat.repository.GroupRepository; // Existing Group repository
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCreationService {

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  public Group createGroup(GroupChatCreationRequest request) {
    if (groupRepository.findByName(request.groupName()).isPresent()) {
      throw new InvalidGroupCreationException("Group with this name already exists");
    }

    Set<User> members = new HashSet<>();
    for (Long memberId : request.memberIds()) {
      User user =
          userRepository
              .findById(memberId)
              .orElseThrow(
                  () -> new InvalidGroupCreationException("Invalid member ID: " + memberId));
      members.add(user);
    }

    Group newGroup = Group.builder().name(request.groupName()).members(members).build();

    return groupRepository.save(newGroup);
  }
}
