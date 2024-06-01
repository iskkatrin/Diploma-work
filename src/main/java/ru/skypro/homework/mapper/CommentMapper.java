package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.entity.CommentEntity;


@Service
public class CommentMapper {

    private final MapperConfig mapper;

    @Autowired
    public CommentMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity) {
        CommentDTO map = mapper.getMapper().map(commentEntity, CommentDTO.class);
        return map;
    }

    public CommentEntity commentDTOToCommentEntityWithoutId(CommentDTO commentDTO) {
        CommentEntity commentEntity = mapper.getMapper().map(commentDTO, CommentEntity.class);
        //не знаю откуда брать ad id
        commentEntity.setAdId(-1L);
        return commentEntity;
    }

}
