package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
//import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AdsService {

    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private AdMapper adMapper;
    @Autowired
    private ImageService imageService;

    private static final Logger log = LoggerFactory.getLogger(AdsService.class);
//
//    public AdsService(AdMapper adMapper) {
//
//        this.adMapper = adMapper;
//    }

    public AdDTO getAdDTO(AdEntity adEntity) {
        return adMapper.adEntityToAdDTO(adEntity);
    }

    public AdEntity getAd(AdDTO adDTO) {
        return adMapper.adDTOToAdEntityWithoutId(adDTO);
    }

    public List<AdDTO> getAllAds() {
        List<AdEntity> adEntities = adsRepository.findAll();
        if (adEntities.isEmpty()) {
            return null;
        }
        return adEntities.stream()
                .map(adMapper::adEntityToAdDTO)
                .collect(Collectors.toList());
    }

    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, UserEntity userEntity) {
        AdEntity adEntity = adMapper.createOrUpdateAdToAdEntity(properties);
        adEntity.setUserEntity(userEntity);
        ImageEntity imageEntity = new  ImageEntity();
        try {
            imageEntity.setData(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageEntity.setMediaType(image.getContentType());
        imageEntity.setFileSize(image.getSize());
        adEntity.setImageEntity(imageEntity);

        // Сохранение изображения и установка пути к изображению в сущность
        adEntity.setImage(image.getOriginalFilename());
        AdEntity savedAd = adsRepository.save(adEntity);
        return adMapper.adEntityToAdDTO(savedAd);
    }

    public List<CommentEntity> getCommentsForAd(int id) {
            return commentRepository.findByAdId((long) id);
    }

    public CommentEntity addCommentToAd(int id, CreateOrUpdateComment comment) {
        CommentEntity newComment = new CommentEntity();
        newComment.setAdId((long) id);
        newComment.setText(comment.getText());
        return commentRepository.save(newComment); // Сохранение нового комментария
    }

    public AdsDTO getAds() {
        AdsDTO adsDTO = new AdsDTO();
        List<AdDTO> result = new ArrayList<>();
        List<AdEntity> all = adsRepository.findAll();

        for (AdEntity adEntity : all) {
            result.add(adMapper.adEntityToAdDTO(adEntity));
        }
        adsDTO.setResults(result);
        adsDTO.setCount(result.size());
        return adsDTO;
    }

    public ExtendedAd getAdById(int id) {
        Optional<AdEntity> adEntityOptional = adsRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            return adMapper.adEntityToExtendedAd(adEntity); // Преобразование в ExtendedAd
        } else {
            throw new AdNotFoundException("Ad not found");
        }
    }

    public void removeAd(int id) {
        adsRepository.deleteById((long) id);
    }

    public AdDTO updateAd(int id, CreateOrUpdateAd ad) {
        Optional<AdEntity> adEntityOptional = adsRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            adMapper.updateAdEntityFromDto(ad, adEntity);
            AdEntity updatedAd = adsRepository.save(adEntity);
            return adMapper.adEntityToAdDTO(updatedAd); // Преобразование и возврат обновленного объявления
        } else {
            throw new AdNotFoundException("Ad not found");
        }
    }

    public void updateAdImage(int id, MultipartFile image) {
        Optional<AdEntity> adEntityOptional = adsRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            log.info("without if------------------------------------");
            AdEntity adEntity = adEntityOptional.get();
            adEntity.setImage(image.getOriginalFilename());
            ImageEntity imageEntity = new ImageEntity();
            try {
                imageEntity.setData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageEntity.setMediaType(image.getContentType());
            imageEntity.setFileSize(imageEntity.getFileSize());
            adEntity.setImageEntity(imageEntity);
            adsRepository.save(adEntity);
            imageService.save(imageEntity);

        } else {
            throw new AdNotFoundException("Ad not found");
        }
    }

    public AdsDTO getAdsForLoggedInUser(UserEntity userEntity) {
        AdsDTO adsDTO = new AdsDTO();
        List<AdEntity> adEntity1 = userEntity.getAdEntity();
        List<AdDTO> result = new ArrayList<>(List.of());
        for (AdEntity adEntity : adEntity1) {
            result.add(getAdDTO(adEntity));
            log.info("adDto name is: {}", adEntity.getTitle());
        }
        adsDTO.setResults(result);
        adsDTO.setCount(result.size());
        return adsDTO;
    }


    public boolean isAuthorAd(String email, Long adId) {

        AdEntity adEntity = adsRepository.findById(adId).orElseThrow(RuntimeException::new);
        return adEntity.getUserEntity().getEmail().equals(email);
    }

    public AdEntity findById(Long id) {
        try {
            return adsRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}