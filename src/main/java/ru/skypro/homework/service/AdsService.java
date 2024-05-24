package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdsRepository;

import java.util.List;

@Service

public class AdsService {
    private final AdsRepository adsRepository;

    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    public List<Ad> getAllAds() {
    }

    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image) {
    }

    public List<Comment> getCommentsForAd(int id) {
    }

    public Comment addCommentToAd(int id, CreateOrUpdateComment comment) {
    }

    public ExtendedAd getAdById(int id) {
    }

    public void removeAd(int id) {
    }

    public Ad updateAd(int id, CreateOrUpdateAd ad) {
    }

    public void updateAdImage(int id, MultipartFile image) {
    }

    public List<Ad> getAdsForLoggedInUser() {
    }
}

   
