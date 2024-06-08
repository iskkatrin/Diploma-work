package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.NotEditUserPasswordException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

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

    //Находит пользователя по его идентификатору.
    public UserEntity findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    //Преобразует сущность пользователя в DTO.
    public UserDTO getUserDTO(UserEntity userEntity) {
        return userMapper.userEntityToUserDTO(userEntity);
    }

    //Находит пользователя по его идентификатору.
    public UserEntity getUserById(Long id) {
        UserEntity byUserId = userRepository.findByUserId(id);
        if (byUserId == null) {
            throw new RuntimeException();
        }
        return byUserId;
    }

    //Сохраняет пользователя.
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    //Находит пользователя по его email.
    public UserEntity findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    //Обновляет информацию о пользователе.
    public UpdateUser updateUser(Long userId, UpdateUser updateUser) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (updateUser.getFirstName() != null) {
            userEntity.setFirstName(updateUser.getFirstName());
        }
        if (updateUser.getLastName() != null) {
            userEntity.setLastName(updateUser.getLastName());
        }
        if (updateUser.getPhone() != null) {
            userEntity.setPhone(updateUser.getPhone());
        }
        saveUser(userEntity);
        return updateUser;
    }

    //Находит DTO пользователя по его идентификатору.
    public UserDTO findUserDTO(Long userId) {
        return getUserDTO(findUser(userId));
    }

    //Обновляет пароль пользователя.
    public void updatePassword(Long userId, NewPassword newPassword) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity.getPassword().equals(newPassword.getCurrentPassword())) {
            userEntity.setPassword(newPassword.getNewPassword());
        } else {
            throw new NotEditUserPasswordException("not edited password");
        }
    }
}
