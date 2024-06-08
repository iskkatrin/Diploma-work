package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
//import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;

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

    private final AdMapper adMapper;

    public AdsService(AdMapper adMapper) {
        this.adMapper = adMapper;
    }

    public AdDTO getAdDTO(AdEntity adEntity) {
        return adMapper.adEntityToAdDTO(adEntity);
    }

    public AdEntity getAd(AdDTO adDTO) {
        return adMapper.adDTOToAdEntityWithoutId(adDTO);
    }

    public List<AdDTO> getAllAds() {
        List<AdEntity> adEntities = adsRepository.findAll();
        return adEntities.stream()
                .map(adMapper::adEntityToAdDTO)
                .collect(Collectors.toList());
    }

    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) {
        AdEntity adEntity = adMapper.createOrUpdateAdToAdEntity(properties);
        // Сохранение изображения и установка пути к изображению в сущность
        adEntity.setImage(image.getOriginalFilename());
        AdEntity savedAd = adsRepository.save(adEntity);
        return adMapper.adEntityToAdDTO(savedAd);
    }

    public CommentEntity addCommentToAd(Integer id, CreateOrUpdateComment comment) {
        CommentEntity newComment = new CommentEntity();
        AdEntity byId;
        try {
            byId = findById(id.longValue());
        } catch (Exception e) {
            throw new RuntimeException("не найдено объявление по его id");
        }
        newComment.setAdEntity(byId);
        newComment.setText(comment.getText());
        return commentRepository.save(newComment); // Сохранение нового комментария
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
            AdEntity adEntity = adEntityOptional.get();
            adEntity.setImage(image.getOriginalFilename());
            adsRepository.save(adEntity);
        } else {
            throw new AdNotFoundException("Ad not found");
        }
    }

    public List<AdDTO> getAdsForLoggedInUser() {
        return null;
    }

    public boolean isAuthorAd(String email, Long adId) {

        AdEntity adEntity = adsRepository.findById(adId).orElseThrow(RuntimeException::new);
        return adEntity.getUserEntity().getEmail().equals(email);
    }

    public AdEntity findById(Long id) {
        return adsRepository.findById(id).get();
    }
}