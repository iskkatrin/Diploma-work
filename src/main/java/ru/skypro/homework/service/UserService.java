package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
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

