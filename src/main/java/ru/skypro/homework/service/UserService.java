package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.NotEditUserPasswordException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;
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

    public UserEntity findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    public UserDTO getUserDTO(UserEntity userEntity) {
        return userMapper.userEntityToUserDTO(userEntity);
    }

    public UserEntity getUser(UserDTO userDTO) {
        return userMapper.userDTOToUserEntity(userDTO);
    }


    public UserEntity getUserById(Long id) {
        UserEntity byUserId = userRepository.findByUserId(id);
        if (byUserId == null) {
            throw new RuntimeException();
        }
        return byUserId;
    }
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

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

    public UserDTO findUserDTO(Long userId) {
        return getUserDTO(findUser(userId));
    }

    public void updatePassword(Long userId, NewPassword newPassword) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity.getPassword().equals(newPassword.getCurrentPassword())) {
            userEntity.setPassword(newPassword.getNewPassword());
        } else {
            throw new NotEditUserPasswordException("not edited password");
        }
    }
    public void saveAvatar (MultipartFile image, UserEntity userEntity) {
        ImageEntity imageEntity = new  ImageEntity();
        try {
            imageEntity.setData(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageEntity.setMediaType(image.getContentType());
        imageEntity.setFileSize(image.getSize());
        userEntity.setImageEntity(imageEntity);

        UserEntity saved = userRepository.save(userEntity);
        userMapper.userEntityToUserDTO(saved);
    }
}


