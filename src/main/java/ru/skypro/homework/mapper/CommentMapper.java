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
        return mapper.getMapper().map(commentEntity, CommentDTO.class);
    }

    public CommentEntity commentDTOToCommentEntityWithoutId(CommentDTO commentDTO) {
        CommentEntity commentEntity = mapper.getMapper().map(commentDTO, CommentEntity.class);
        //не знаю откуда брать ad id
        commentEntity.setAdEntity(null);
        return commentEntity;
    }

}
