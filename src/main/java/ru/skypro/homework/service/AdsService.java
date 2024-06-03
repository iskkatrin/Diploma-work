package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
//import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@AllArgsConstructor
public class AdsService {
    private final AdsRepository adsRepository;
    private final AdMapper mapper;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;



    public AdDTO getAdDTO(AdEntity adEntity) {
//        return AdMapper.INSTANCE.adToAdDTO(ad);
        return null;
    }

    public AdEntity getAd(AdDTO adDTO) {
//        return AdMapper.INSTANCE.adDTOToAd(adDTO);
        return null;
    }

    public List<AdDTO> getAllAds() {
        List<AdDTO> listAd = adsRepository.findAll().stream()
                .map(e->mapper.adEntityToAdDTO(e)).collect(Collectors.toList());
                return listAd;
    }

    //private Long id;
    //private Long authorId;
    //private String image;
    //private Integer price;
    //private String title;

    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) throws IOException {

        Long id = userRepository.findByEmail(authentication.getName()).getUserId();
        AdEntity adEntity = AdEntity.builder()
                .price(properties.getPrice())
                .title(properties.getTitle())
                .authorId(id)
                .description(properties.getDescription())
                .build();
        adsRepository.save(adEntity);


        Path filePath = Path.of("/image", adEntity + "." + getExtensions(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        ImageEntity imageEntity = imageRepository
                .findById(adEntity.getImageEntity().getImageId()).orElse(new ImageEntity());
        imageEntity.setFilePath(filePath.toString());
        imageEntity.setFileSize(image.getSize());
        imageEntity.setMediaType(image.getContentType());
        imageEntity.setData(image.getBytes());
        imageRepository.save(imageEntity);
        adEntity.setImageEntity(imageEntity);


        return mapper.adEntityToAdDTO(adEntity);
    }
    private String getExtensions(String fileName) {
        String extentions = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extentions;
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
}



