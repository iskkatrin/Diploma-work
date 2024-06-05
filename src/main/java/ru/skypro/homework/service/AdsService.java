package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
//import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdsRepository;

import java.util.List;
@Slf4j
@Service
public class AdsService {
    private final AdsRepository adsRepository;


    public AdDTO getAdDTO(AdEntity adEntity) {
//        return AdMapper.INSTANCE.adToAdDTO(ad);
        return null;
    }

    public AdEntity getAd(AdDTO adDTO) {
//        return AdMapper.INSTANCE.adDTOToAd(adDTO);
        return null;
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

    public List<CommentEntity> getCommentsForAd(int id) {
        return null;
    }

    public CommentEntity addCommentToAd(int id, CreateOrUpdateComment comment) {
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


    public boolean isAuthorAd(String username, Long adId) {

        AdEntity adEntity = adsRepository.findById(adId).orElseThrow(RuntimeException::new);
        return adEntity.getAuthor().getUsername().equals(username);
    }
}



