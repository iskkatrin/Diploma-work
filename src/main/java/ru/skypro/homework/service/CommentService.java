package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Autowired
    private AdsService adsService;
    @Autowired
    private UserService userService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDTO addComment(Authentication authentication, Integer adId, CreateOrUpdateComment comment) {
        CommentEntity newComment = new CommentEntity();
        UserEntity userEntity = userService.findByUsername(authentication.getName()).get();
//        UserEntity userEntity = userService.findByUsername("admin@example.com").get();
        newComment.setAuthor(userEntity.getUserId().intValue());
        newComment.setUserEntity(userEntity);
        newComment.setAdId((long) adId);
        newComment.setAdEntity(adsService.findById(adId.longValue()));
        newComment.setText(comment.getText());
        newComment.setAuthorFirstName(userEntity.getFirstName());
        newComment.setAuthorImage("/image/" + userEntity.getImageEntity().getImageId());
        CommentEntity savedComment = commentRepository.save(newComment);
        return commentMapper.commentEntityToCommentDTO(savedComment);
    }

    public CommentsDTO getComments(long adId) {
        CommentsDTO comments = new CommentsDTO();
        List<CommentDTO> result = new ArrayList<>();
        List<CommentEntity> allByAdId = commentRepository.findByAdId(adId);

        for (CommentEntity commentEntity : allByAdId) {
            result.add(commentMapper.commentEntityToCommentDTO(commentEntity));
        }
        comments.setResults(result);
        comments.setCount(result.size());
        return comments;
    }


    public void deleteComment(long adId, long commentId) {
//        commentRepository.findByCommentId(commentId);
//        commentRepository.deleteByCommentIdAndAdId(adId, commentId);
        commentRepository.delete(commentRepository.findByCommentId(commentId));
    }

    public CommentDTO updateComment(long adId, long commentId, CreateOrUpdateComment comment) {
        CommentEntity existingComment = commentRepository.findByCommentIdAndAdId(commentId, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with adId: " + adId + " and commentId: " + commentId));
        existingComment.setText(comment.getText());
        return commentMapper.commentEntityToCommentDTO(commentRepository.save(existingComment));

    }
}



