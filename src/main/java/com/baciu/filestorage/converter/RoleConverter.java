package com.baciu.filestorage.converter;

import com.baciu.filestorage.dto.RoleDTO;
import com.baciu.filestorage.entity.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleConverter {

    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    public Set<RoleDTO> toDTO(Set<Role> roles) {
        Set<RoleDTO> rolesDTO = new HashSet<RoleDTO>();

        for (Role role : roles)
            rolesDTO.add(toDTO(role));

        return rolesDTO;
    }



}