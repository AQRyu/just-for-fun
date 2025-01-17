package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.groupchat.exception.GroupNotFoundException;
import com.aqryuz.backend.groupchat.exception.UnauthorizedException;
import com.aqryuz.backend.groupchat.exception.UserNotFoundException;
import com.aqryuz.backend.groupchat.model.Group;
import com.aqryuz.backend.groupchat.repository.GroupRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupMemberManagementServiceImpl implements GroupMemberManagementService {

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  @Transactional
  public Group addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser) {
    Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);

    if (!group.getMaster().equals(currentUser)) {
      throw new UnauthorizedException();
    }

    Set<User> membersToAdd = new HashSet<>();
    for (Long memberId : memberIds) {
      User member = userRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
      membersToAdd.add(member);
    }

    group.getMembers().addAll(membersToAdd);
    return groupRepository.save(group);
  }

  @Transactional
  public Group removeMembersFromGroup(Long groupId, Set<Long> memberIds, User currentUser) {
    Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);

    if (!group.getMaster().equals(currentUser)) {
      throw new UnauthorizedException();
    }

    Set<User> membersToRemove = new HashSet<>();
    for (Long memberId : memberIds) {
      User member = userRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
      membersToRemove.add(member);
    }

    group.getMembers().removeAll(membersToRemove);
    return groupRepository.save(group);
  }
}
