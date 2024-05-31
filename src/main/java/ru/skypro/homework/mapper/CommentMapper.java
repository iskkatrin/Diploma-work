package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.User;

import javax.xml.stream.events.Comment;
import java.util.Optional;
@Mapper
public interface CommentMapper {
    //ru.skypro.homework.mapper.CommentMapper INSTANCE = Mappers.getMapper(ru.skypro.homework.mapper.CommentMapper.class);
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    CommentDTO commentToCommentDTO(CommentEntity commentEntity);
    CommentEntity commentDTOToComment(CommentDTO commentDTO);

}
