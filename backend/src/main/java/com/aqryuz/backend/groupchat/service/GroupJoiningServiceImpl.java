package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.groupchat.exception.GroupMasterRequiredException;
import com.aqryuz.backend.groupchat.exception.ResourceNotFoundException;
import com.aqryuz.backend.groupchat.model.Group;
import com.aqryuz.backend.groupchat.repository.GroupRepository;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupJoiningServiceImpl implements GroupJoiningService {
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  @Override
  public Group addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser) {
    Group group = groupRepository.findById(groupId).orElseThrow(ResourceNotFoundException::new);

    if (!group.getMaster().equals(currentUser)) {
      throw new GroupMasterRequiredException();
    }

    Set<User> newMembers = new HashSet<>();
    for (Long memberId : memberIds) {
      User member = userRepository.findById(memberId).orElseThrow(ResourceNotFoundException::new);
      newMembers.add(member);
    }

    group.getMembers().addAll(newMembers);

    return groupRepository.save(group);
  }
}
