package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    public UserDTO getUserDTO(User user) {
        return userMapper.userToUserDTO(user);
    }

    public User getUser(UserDTO userDTO) {
        return userMapper.userDTOToUser(userDTO);
    }


    public User getUserById(Long id) {
        User byUserId = userRepository.findByUserId(id);
        if (byUserId == null) {
            throw new RuntimeException();
        }
        return byUserId;
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
}


