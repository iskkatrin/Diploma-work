package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.CommentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(int adId, CreateOrUpdateComment comment) {
        return null;
    }

    public List<Comment> getComments(int adId) {
        return commentRepository.findByAdId(adId);
    }



    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}


