package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(int adId, CreateOrUpdateComment comment) {
        return null;
    }
}


