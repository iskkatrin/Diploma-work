package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDTO addComment(Long adId, CreateOrUpdateComment comment) {
        CommentEntity newComment = new CommentEntity();
        newComment.setAdId(adId);
        newComment.setText(comment.getText());

        CommentEntity savedComment = commentRepository.save(newComment);
        return commentMapper.commentEntityToCommentDTO(savedComment);
    }

    public CommentsDTO getComments(Integer adId) {
        CommentsDTO comments = new CommentsDTO();
        List<CommentDTO> result = new ArrayList<>();
        List<CommentEntity> allByAdId = commentRepository.findByAdId(adId.longValue()); // явное преобразование Integer в long

        for (CommentEntity commentEntity : allByAdId) {
            result.add(commentMapper.commentEntityToCommentDTO(commentEntity));
        }
        comments.setResults(result);
        comments.setCount(result.size());
        return comments;
    }


    public void deleteComment(int adId, long commentId) {
        commentRepository.deleteById(commentId);
    }


    public CommentDTO updateComment(int adId, Long commentId, CreateOrUpdateComment comment) {
        CommentEntity existingComment = commentRepository.findByCommentIdAndAdId(commentId, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with adId: " + adId + " and commentId: " + commentId));
        existingComment.setText(comment.getText());
        return commentMapper.commentEntityToCommentDTO(commentRepository.save(existingComment));

    }

}



