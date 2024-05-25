package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findByAdId(long adId);

    Optional<Comment> findByCommentIdAndAdId(long commentId, long adId);
}