package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Service
public class UserMapper {

    private final MapperConfig mapper;

    @Autowired
    public UserMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = mapper.getMapper().map(user, UserDTO.class);
        userDTO.setId(Math.toIntExact(user.getUserId()));
        userDTO.setImage("нужно подправить маппер пока нет реализации с image");
        return userDTO;
    }

    public User userDTOToUser(UserDTO userDTO) {
        return null;
    }
}

