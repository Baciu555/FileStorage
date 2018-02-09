package com.baciu.filestorage.service;

import com.baciu.filestorage.converter.FileConverter;
import com.baciu.filestorage.converter.GroupConverter;
import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.FileDTO;
import com.baciu.filestorage.dto.GroupDTO;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.entity.File;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.exception.GroupNotExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.repository.FileRepository;
import com.baciu.filestorage.repository.GroupRepository;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupConverter groupConverter;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private FileRepository fileRepository;

    public GroupDTO getGroup(Long id) {
        if (!groupRepository.exists(id)) return null;

        return groupConverter.toDTOData(groupRepository.findOne(id));
    }

    public Set<GroupDTO> getGroupByUserId(Long id) {
        User user = userRepository.findOne(id);
        if (user.getGroups() == null)
            return null;

        List<Integer> membersCountList = new ArrayList<>();
        List<Integer> filesCountList = new ArrayList<>();
        setMembersCountList(membersCountList, user.getGroups());
        setFilesCountList(filesCountList, user.getGroups());

        Set<GroupDTO> groups = groupConverter.toDTO(user.getGroups());
        groups = setFilesAndMembersCount(groups, membersCountList, filesCountList);

        return groups;
    }

    private void setMembersCountList(List<Integer> membersCount, Set<Group> groups) {
        Iterator<Group> it = groups.iterator();
        while (it.hasNext())
            membersCount.add(it.next().getUsers().size());
    }

    private void setFilesCountList(List<Integer> filesCount, Set<Group> groups) {
        Iterator<Group> it = groups.iterator();
        while (it.hasNext())
            filesCount.add(it.next().getFiles().size());
    }

    private Set<GroupDTO> setFilesAndMembersCount(Set<GroupDTO> groups, List<Integer> membersCount, List<Integer> filesCount) {
        Iterator<GroupDTO> it = groups.iterator();
        Set<GroupDTO> groupsDTO = new HashSet<>(0);
        int i = 0;
        while (it.hasNext()) {
            GroupDTO groupDTO = it.next();
            groupDTO.setMembersCount(membersCount.get(i));
            groupDTO.setFilesCount(filesCount.get(i));
            groupsDTO.add(groupDTO);
            i++;
        }
        return groupsDTO;
    }

    public GroupDTO addGroupUsers(String email, Long id) throws UserNotExistsException, GroupNotExistsException {
        if (!groupRepository.exists(id))
            throw new GroupNotExistsException();

        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotExistsException();

        Group group = groupRepository.findOne(id);
        group.getUsers().add(user);
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroupFiles(Set<FileDTO> filesDTO, Long id) {
        if (!groupRepository.exists(id))
            System.out.println("false");

        Group group = groupRepository.findOne(id);
        group.setFiles(fileConverter.toEntity(filesDTO));
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroupFile(FileDTO fileDTO, Long id) throws GroupNotExistsException {
        if (!groupRepository.exists(id))
            throw new GroupNotExistsException();

        Group group = groupRepository.findOne(id);
        File file = fileRepository.findOne(fileDTO.getId());
        group.getFiles().add(fileConverter.toEntity(fileDTO));
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroup(Group group) {
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO updateGroup(Group group) {
        Group existedGroup = groupRepository.findOne(group.getId());
        existedGroup.setName(group.getName());
        existedGroup.setDescription(group.getDescription());
        return groupConverter.toDTO(groupRepository.save(existedGroup));
    }

    public void deleteGroup(Long id) throws GroupNotExistsException {
        Group group = groupRepository.findOne(id);
        if(group == null) throw new GroupNotExistsException();

        groupRepository.delete(id);
    }

    public void deleteGroupFile(Long groupId, Long fileId) {
        Group group = groupRepository.findOne(groupId);
        File file = fileRepository.findOne(fileId);

        Set<File> files = group.getFiles();
        files.remove(file);
        groupRepository.save(group);
    }

    public GroupDTO addUser(Long groupId, Long userId) {
        Group group = groupRepository.findOne(groupId);
        User user = userRepository.findOne(userId);

        group.getUsers().add(user);
        return groupConverter.toDTO(groupRepository.save(group));
    }
}
