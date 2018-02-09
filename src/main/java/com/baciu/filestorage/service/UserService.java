package com.baciu.filestorage.service;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public UserDTO getUser(Long id) {
        if(!userRepository.exists(id)) return null;

        return userConverter.toDTO(userRepository.findOne(id), true);
    }

    public UserDTO addUser(UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        System.out.println(userDTO.toString());
        User user = userConverter.toEntity(userDTO);

        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UsernameExistsException();

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException();

        user.setRegisterDate(new Date());

        return userConverter.toDTO(userRepository.save(user), false);
    }

    public UserDTO getByEmailAndPassword(String email, String password) throws UserNotExistsException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null)
            throw new UserNotExistsException();

        return userConverter.toDTO(user, false);
    }

    public UserDTO getByEmail(String email) throws UserNotExistsException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotExistsException();

        return userConverter.toDTO(user, false);
    }

    public UserDTO updateUser(UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        User user = userConverter.toEntity(userDTO);
        User existedUser = userRepository.findOne(user.getId());

        if (userRepository.findByUsername(user.getUsername()) != null && !user.getUsername().equals(existedUser.getUsername()))
           throw new UsernameExistsException();

        if (userRepository.findByEmail(user.getEmail()) != null && !user.getEmail().equals(existedUser.getEmail()))
            throw new EmailExistsException();

        existedUser.setUsername(user.getUsername());
        existedUser.setEmail(user.getEmail());

        return userConverter.toDTO(userRepository.save(existedUser), false);
    }

    public void deleteUser(Long id) throws UserNotExistsException {
        User user = userRepository.findOne(id);
        if(user == null) throw new UserNotExistsException();

        userRepository.delete(id);
    }


}
