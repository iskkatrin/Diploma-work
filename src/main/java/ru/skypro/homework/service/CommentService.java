package ru.skypro.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdsService adsService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, AdsService adsService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.adsService = adsService;
    }

    public CommentDTO addComment(long adId, CreateOrUpdateComment comment) {
        log.info("Добавление нового комментария к объявлению с id: {}", adId);
        try {
            AdEntity adEntity = adsService.findById(adId);
            CommentEntity newComment = new CommentEntity();
            newComment.setAdEntity(adEntity);
            newComment.setText(comment.getText());

            CommentEntity savedComment = commentRepository.save(newComment);
            log.info("Комментарий успешно добавлен. ID комментария: {}", savedComment.getCommentId());
            return commentMapper.commentEntityToCommentDTO(savedComment);
        } catch (AdNotFoundException e) {
            log.error("Ошибка добавления комментария: объявление не найдено. adId: {}", adId);
            throw new AdNotFoundException("Объявление не найдено");
        }
    }

    public CommentsDTO getComments(long adId) {
        log.info("Получение комментариев к объявлению с id: {}", adId);
        try {
            AdEntity adEntity = adsService.findById(adId);
            List<CommentEntity> allByAdId = adEntity.getCommentEntity();
            List<CommentDTO> result = new ArrayList<>();

            for (CommentEntity commentEntity : allByAdId) {
                result.add(commentMapper.commentEntityToCommentDTO(commentEntity));
            }
            CommentsDTO comments = new CommentsDTO();
            comments.setResults(result);
            comments.setCount(result.size());
            log.info("Комментарии успешно получены. Количество комментариев: {}", result.size());
            return comments;
        } catch (AdNotFoundException e) {
            log.error("Ошибка получения комментариев: объявление не найдено. adId: {}", adId);
            throw new CommentNotFoundException("Объявление не найдено");
        }
    }

    public void deleteComment(long adId, long commentId) {
        log.info("Удаление комментария с id: {} из объявления с id: {}", commentId, adId);
        try {
            AdEntity adEntity = adsService.findById(adId);
            List<CommentEntity> commentEntities = adEntity.getCommentEntity();
            for (CommentEntity commentEntity : commentEntities) {
                if (commentEntity.getCommentId().equals(commentId)) {
                    commentRepository.delete(commentEntity);
                    log.info("Комментарий успешно удален.");
                    return;
                }
            }
            log.error("Ошибка удаления комментария: комментарий не найден. commentId: {}, adId: {}", commentId, adId);
            throw new CommentNotFoundException("Комментарий не найден");
        } catch (AdNotFoundException e) {
            log.error("Ошибка удаления комментария: объявление не найдено. adId: {}", adId);
            throw new CommentNotFoundException("Объявление не найдено");
        }
    }

    public CommentDTO updateComment(long adId, long commentId, CreateOrUpdateComment comment) {
        log.info("Обновление комментария с id: {} в объявлении с id: {}", commentId, adId);
        try {
            AdEntity adEntity = adsService.findById(adId);
            List<CommentEntity> commentEntities = adEntity.getCommentEntity();
            for (CommentEntity existingComment : commentEntities) {
                if (existingComment.getCommentId().equals(commentId)) {
                    existingComment.setText(comment.getText());
                    CommentEntity savedComment = commentRepository.save(existingComment);
                    log.info("Комментарий успешно обновлен.");
                    return commentMapper.commentEntityToCommentDTO(savedComment);
                }
            }
            log.error("Ошибка обновления комментария: комментарий не найден. commentId: {}, adId: {}", commentId, adId);
            throw new CommentNotFoundException("Комментарий не найден");
        } catch (AdNotFoundException e) {
            log.error("Ошибка обновления комментария: объявление не найдено. adId: {}", adId);
            throw new CommentNotFoundException("Объявление не найдено");
        }
    }
}



