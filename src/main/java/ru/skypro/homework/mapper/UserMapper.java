package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

import java.util.Optional;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(Optional<User> user);
    User userDTOToUser(UserDTO userDTO);
}
