package com.baciu.filestorage.controller;

import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws UserNotExistsException {
        return new ResponseEntity<>(userService.getByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword()), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUser(id);
        if (userDTO == null)
            return new ResponseEntity<>("nie znaleziono uzytkownika", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        UserDTO user = userService.addUser(userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("users")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        UserDTO user = userService.updateUser(userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws UserNotExistsException {
        userService.deleteUser(id);

        return new ResponseEntity<>("uzytkownik usuniety", HttpStatus.OK);
    }

}
