package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GroupConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private FileConverter fileConverter;

    public Group toEntity(GroupDTO groupDTO) {
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        if (groupDTO.getUsers() != null)
            group.setUsers(userConverter.toEntity(groupDTO.getUsers()));
        return group;
    }

    public GroupDTO toDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        return groupDTO;
    }

    public GroupDTO toDTOData(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setFiles(fileConverter.toDTO(group.getFiles()));
        groupDTO.setUsers(userConverter.toDTO(group.getUsers()));
        return groupDTO;
    }

    public Set<GroupDTO> toDTO(Set<Group> groups) {
        Set<GroupDTO> groupsDTO = new HashSet<>(0);
        for (Group group : groups)
            groupsDTO.add(toDTO(group));
        return groupsDTO;
    }

}
