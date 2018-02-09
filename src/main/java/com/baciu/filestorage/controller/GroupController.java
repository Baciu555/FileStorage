package com.baciu.filestorage.controller;

import com.baciu.filestorage.converter.GroupConverter;
import com.baciu.filestorage.dto.FileDTO;
import com.baciu.filestorage.dto.GroupDTO;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.exception.GroupNotExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins="*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupConverter groupConverter;

    @GetMapping("groups/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        GroupDTO group = groupService.getGroup(id);
        if (group == null)
            return new ResponseEntity<>("nie znaleziono grupy", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("groups/user/{id}")
    public ResponseEntity<?> getGroupByUserId(@PathVariable Long id) {
        Set<GroupDTO> groups = groupService.getGroupByUserId(id);
        if (groups == null)
            return new ResponseEntity<>("nie nalezysz do zadnej grupy", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping("groups")
    public ResponseEntity<?> addGroup(@Valid @RequestBody GroupDTO groupDTO) {
        GroupDTO group = groupService.addGroup(groupConverter.toEntity(groupDTO));

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping("groups/{id}/users")
    public ResponseEntity<?> addGroupUsers(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) throws UserNotExistsException, GroupNotExistsException {
        System.out.println(id);
        System.out.println(userDTO);
        groupService.addGroupUsers(userDTO.getEmail(), id);
        return ResponseEntity.ok("uzytkownik dodany do grupy");
    }

    @PostMapping("groups/{id}/files")
    public ResponseEntity<?> addGroupFiles(@RequestBody Set<FileDTO> filesDTO, @PathVariable("id") Long id) {
        System.out.println(filesDTO.toString());
        System.out.println(id);
        groupService.addGroupFiles(filesDTO, id);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("groups/{id}/file")
    public ResponseEntity<?> addGroupFile(@RequestBody FileDTO fileDTO, @PathVariable("id") Long id) throws GroupNotExistsException {
        System.out.println(fileDTO.toString());
        System.out.println(id);
        groupService.addGroupFile(fileDTO, id);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("groups")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO) {
        GroupDTO group = groupService.updateGroup(groupConverter.toEntity(groupDTO));

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("groups/{groupId}/user/add/{userId}")
    public ResponseEntity<?> addUserIntoGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        groupService.addUser(groupId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Long id) throws GroupNotExistsException {
        groupService.deleteGroup(id);

        return new ResponseEntity<>("grupa usunieta", HttpStatus.OK);
    }

    @DeleteMapping("groups/{groupId}/files/{fileId}")
    public ResponseEntity<?> deleteGroupFile(@PathVariable("groupId") Long groupId, @PathVariable("fileId") Long fileId) {
        groupService.deleteGroupFile(groupId, fileId);
        return new ResponseEntity<>("plik usuniety", HttpStatus.OK);
    }

}
