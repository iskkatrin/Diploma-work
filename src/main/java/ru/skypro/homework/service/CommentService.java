package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.repository.CommentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(int adId, CreateOrUpdateComment comment) {
        Comment newComment = new Comment();
        newComment.setAdId(adId);
        newComment.setText(comment.getText());
        return commentRepository.save(newComment);
    }

    public List<Comment> getComments(int adId) {
        return commentRepository.findByAdId(adId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        Optional<Comment> existingComment = commentRepository.findByCommentIdAndAdId(commentId, adId);
        if (existingComment.isPresent()) {
            Comment updatedComment = existingComment.get();
            updatedComment.setText(comment.getText());
            return commentRepository.save(updatedComment);
        } else {
            throw new CommentNotFoundException("Comment not found with adId: " + adId + " and commentId: " + commentId);
        }
    }
}


