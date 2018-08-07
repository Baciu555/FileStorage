package com.baciu.filestorage;


import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.entity.File;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.entity.Role;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.RoleExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.repository.FileRepository;
import com.baciu.filestorage.repository.UserRepository;
import com.baciu.filestorage.service.GroupService;
import com.baciu.filestorage.service.RoleService;
import com.baciu.filestorage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Override
    public void run(String...args) throws Exception {
        logger.info("@@@@Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
        initData();
    }


    private void initData() throws RoleExistsException, EmailExistsException, UsernameExistsException {
        Role role1 = Role.builder()
                .name("ROLE_USER")
                .build();
        Role role2 = Role.builder()
                .name("ROLE_ADMIN")
                .build();

        roleService.add(role1);
        roleService.add(role2);

        User user1 = User.builder()
                .username("username")
                .email("email@gmail.com")
                .password("13456")
                .build();
        User user2 = User.builder()
                .username("testowy")
                .email("email12@gmail.com")
                .password("654321")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        Group group1 = Group.builder()
                .name("grupa1")
                .description("grupa numer jeden")
                .build();
        Group group2 = Group.builder()
                .name("grupa2")
                .description("grupa numer dwa")
                .build();
        groupService.addGroup(group1);
        groupService.addGroup(group2);

        File file = File.builder()
                .name("my photo")
                .description("me and my family")
                .path("/photos/photo1.jpg")
                .size(12551l)
                .user(user1)
                .build();
        fileRepository.save(file);

    }
}