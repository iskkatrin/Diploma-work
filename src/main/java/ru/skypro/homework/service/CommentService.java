package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

@Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentEntity addComment(int adId, CreateOrUpdateComment comment) {
        return null;
    }

    public CommentsDTO getComments(Integer adId) {
        CommentsDTO comments = new CommentsDTO();
        List<CommentDTO> result = new ArrayList<>();
//        List<CommentEntity> allByAdId = commentRepository.findByAdId(adId.longValue());
        List<CommentEntity> allByAdId = commentRepository.findAll();
        for (CommentEntity commentEntity : allByAdId) {
            result.add(commentMapper.commentEntityToCommentDTO(commentEntity));
        }
        comments.setResults(result);
        comments.setCount(result.size());

        return comments;
    }

    public void deleteComment(int adId, int commentId) {
    }

    public CommentEntity updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        return null;
    }
}



