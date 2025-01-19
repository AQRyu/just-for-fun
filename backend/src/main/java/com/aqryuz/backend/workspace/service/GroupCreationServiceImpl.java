package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.workspace.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.workspace.exception.GroupMasterRequiredException;
import com.aqryuz.backend.workspace.exception.InvalidGroupCreationException;
import com.aqryuz.backend.workspace.mapper.GroupChatMapper;
import com.aqryuz.backend.workspace.model.Group;
import com.aqryuz.backend.workspace.repository.GroupRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCreationServiceImpl implements GroupCreationService {

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final GroupChatMapper mapper;

  @Override
  public Group createGroup(GroupChatCreationRequest request, Long userId) {
    groupRepository
        .findByName(request.groupName())
        .ifPresent(
            existingGroup -> {
              throw new InvalidGroupCreationException();
            });

    User user = userRepository.findById(userId).orElseThrow(GroupMasterRequiredException::new);

    Group newGroup = mapper.toGroup(request, Set.of(user));

    return groupRepository.save(newGroup);
  }
}
