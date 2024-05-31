package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdsRepository;

import java.util.List;

@Service

public class AdsService {
    private final AdsRepository adsRepository;


    public AdDTO getAdDTO(Ad ad) {
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    public Ad getAd(AdDTO adDTO) {
        return AdMapper.INSTANCE.adDTOToAd(adDTO);
    }

    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    public List<AdDTO> getAllAds() {
        return null;
    }

    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image) {
        return null;
    }

    public List<Comment> getCommentsForAd(int id) {
        return null;
    }

    public Comment addCommentToAd(int id, CreateOrUpdateComment comment) {
        return null;
    }

    public ExtendedAd getAdById(int id) {
        return null;
    }

    public void removeAd(int id) {
    }

    public AdDTO updateAd(int id, CreateOrUpdateAd ad) {
        return null;
    }

    public void updateAdImage(int id, MultipartFile image) {
    }

    public List<AdDTO> getAdsForLoggedInUser() {
        return null;
    }
}


   
