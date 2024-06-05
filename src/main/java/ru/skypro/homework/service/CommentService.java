package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
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
    private final AdsService adsService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, AdsService adsService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.adsService = adsService;
    }

    public CommentDTO addComment(long adId, CreateOrUpdateComment comment) {
        CommentEntity newComment = new CommentEntity();
        AdEntity byId;
        try {
            byId = adsService.findById(adId);
        } catch (Exception e) {
            throw new RuntimeException("не найдено объявление по его id");
        }
        newComment.setAdEntity(byId);
        newComment.setText(comment.getText());

        CommentEntity savedComment = commentRepository.save(newComment);
        return commentMapper.commentEntityToCommentDTO(savedComment);
    }

    public CommentsDTO getComments(long adId) {
        CommentsDTO comments = new CommentsDTO();
        List<CommentDTO> result = new ArrayList<>();
        AdEntity adEntity = adsService.findById(adId);
        List<CommentEntity> allByAdId = adEntity.getCommentEntity();

        for (CommentEntity commentEntity : allByAdId) {
            result.add(commentMapper.commentEntityToCommentDTO(commentEntity));
        }
        comments.setResults(result);
        comments.setCount(result.size());
        return comments;
    }


    public void deleteComment(long adId, long commentId) {
        List<CommentEntity> commentEntities = adsService.findById(adId).getCommentEntity();
        for (CommentEntity commentEntity : commentEntities) {
            if (commentEntity.getCommentId().equals(commentId)) {
                commentRepository.delete(commentEntity);
                return;
            }
        }
        throw new RuntimeException("не удалось удалить comment");
    }

    public CommentDTO updateComment(long adId, long commentId, CreateOrUpdateComment comment) {
        List<CommentEntity> commentEntities = adsService.findById(adId).getCommentEntity();
        for (CommentEntity existingComment : commentEntities) {
            if (existingComment.getCommentId().equals(commentId)) {
                existingComment.setText(comment.getText());
                return commentMapper.commentEntityToCommentDTO(commentRepository.save(existingComment));
            }
        }
        throw new RuntimeException("не удалось update comment");
    }
}



