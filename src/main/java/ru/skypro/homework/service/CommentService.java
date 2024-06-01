package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity addComment(int adId, CreateOrUpdateComment comment) {
        return null;
    }

    public CommentsDTO getComments(int adId) {
        return null;
    }

    public void deleteComment(int adId, int commentId) {
    }

    public CommentEntity updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        return null;
    }
}



