package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserDTO(Optional<User> user) {
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    public User getUser(UserDTO userDTO) {
        return UserMapper.INSTANCE.userDTOToUser(userDTO);
    }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
