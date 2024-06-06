package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.UserEntity;

@Service
public class UserMapper {

    private final MapperConfig mapper;

    @Autowired
    public UserMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = mapper.getMapper().map(userEntity, UserDTO.class);
        userDTO.setId(userEntity.getUserId().intValue());
        userDTO.setImage("/image/me/" + userEntity.getImageEntity().getImageId());
        return userDTO;
    }

    public UserEntity userDTOToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = mapper.getMapper().map(userDTO, UserEntity.class);
        userEntity.setUserId(userDTO.getId().longValue());
        return userEntity;
    }
}

