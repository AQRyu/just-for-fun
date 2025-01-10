package com.aqryuz.backend.groupchat.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.groupchat.exception.GroupMasterRequiredException;
import com.aqryuz.backend.groupchat.exception.ResourceNotFoundException;
import com.aqryuz.backend.groupchat.model.Group;
import com.aqryuz.backend.groupchat.repository.GroupRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupJoiningServiceImpl implements GroupJoiningService {
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  @Override
  public Group addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser) {
    Group group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found ID: " + groupId));

    if (!group.getMaster().equals(currentUser)) {
      throw new GroupMasterRequiredException("Only the group master can add members");
    }

    Set<User> newMembers = new HashSet<>();
    for (Long memberId : memberIds) {
      User member =
          userRepository
              .findById(memberId)
              .orElseThrow(() -> new ResourceNotFoundException("Invalid member ID: " + memberId));
      newMembers.add(member);
    }

    group.getMembers().addAll(newMembers);

    return groupRepository.save(group);
  }
}
