package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserConverter {

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private GroupConverter groupConverter;

    @Autowired
    private RoleConverter roleConverter;

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRegisterDate(user.getRegisterDate());
        return user;
    }

    public UserDTO toDTO(User user, boolean includeData) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRegisterDate(user.getRegisterDate());
        //userDTO.setRoles(roleConverter.toDTO(user.getRoles()));

        if (includeData) {
            userDTO.setFiles(fileConverter.toDTO(user.getFiles()));
            userDTO.setGroups(groupConverter.toDTO(user.getGroups()));
        }

        return userDTO;
    }

    public Set<UserDTO> toDTO(Set<User> users) {
        Set<UserDTO> usersDTO = new HashSet<UserDTO>();
        for (User u : users)
            usersDTO.add(toDTO(u, false));
        return usersDTO;
    }

    public Set<User> toEntity(Set<UserDTO> usersDTO) {
        Set<User> users = new HashSet<>(0);
        for (UserDTO userDTO : usersDTO)
            users.add(toEntity(userDTO));
        return users;
    }

}
