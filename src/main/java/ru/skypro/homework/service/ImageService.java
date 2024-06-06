package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.NotSaveAvatarEx;
import ru.skypro.homework.repository.ImageRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    @Value("${avatar.image.directory}")
    private String avatarsDir;

    private final UserService userService;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(UserService userService, ImageRepository imageRepository) {
        this.userService = userService;
        this.imageRepository = imageRepository;
    }


    public void uploadImage(Long userId, MultipartFile image) throws IOException {
        UserEntity userEntity = userService.getUserById(userId);

        Path filePath;
        try {
            filePath = Path.of(avatarsDir, userEntity + "." + getExtensions(Objects.requireNonNull(image.getOriginalFilename())));
        } catch (Exception e) {
            throw new NotSaveAvatarEx("ошибка сохранения фотографии в БД");
        }
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


        ImageEntity newImageEntity;
        newImageEntity = imageRepository.findImageByFilePath(filePath.toString());


        if (newImageEntity == null) {
            newImageEntity = new ImageEntity();
        }
        newImageEntity.setImageId(userId);
        newImageEntity.setData(image.getBytes());
        newImageEntity.setFilePath(filePath.toString());
        newImageEntity.setMediaType(image.getContentType());
        newImageEntity.setFileSize(image.getSize());

        imageRepository.save(newImageEntity);
        userEntity.setImageEntity(newImageEntity);
        userService.saveUser(userEntity);
    }

    public ImageEntity findById(Long id) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            return optionalImage.get();
        }
        throw new RuntimeException("not found image by it id");
    }


    private String getExtensions(String fileName) {
        String extentions = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.debug("extentions is: {}", extentions);
        return extentions;
    }
}
