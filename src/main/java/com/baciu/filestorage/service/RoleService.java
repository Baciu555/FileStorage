package com.baciu.filestorage.service;

import com.baciu.filestorage.entity.Role;
import com.baciu.filestorage.exception.RoleExistsException;
import com.baciu.filestorage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role add(Role role) {
        roleRepository.findByName(role.getName())
                .ifPresent(x -> new RoleExistsException());

        return roleRepository.save(role);
    }

}
