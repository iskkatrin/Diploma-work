package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<AdEntity, Long> {
    List<CommentEntity> findByAdId(Long adId);
}