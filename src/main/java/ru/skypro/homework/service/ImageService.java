package ru.skypro.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.NotSaveAvatarEx;
import ru.skypro.homework.repository.ImageRepository;

import javax.transaction.Transactional;
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
        Optional<User> user = userService.getUserById(userId); //поиск нужного студента

        Path filePath;
        try {
            filePath = Path.of(avatarsDir, user.get().toString() + "." + getExtensions(Objects.requireNonNull(image.getOriginalFilename())));
        } catch (Exception e) {
            throw new NotSaveAvatarEx("ошибка сохранения фотографии в БД");
        }
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        //работа с потоками
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }


        Image newImage;
        newImage = imageRepository.findImageByFilePath(filePath.toString());


        if (newImage == null) {
            newImage = new Image();
        }
        newImage.setImageId(userId);
        newImage.setData(image.getBytes());
        newImage.setFilePath(filePath.toString());
        newImage.setMediaType(image.getContentType());
        newImage.setFileSize(image.getSize());

//        user.setAvatar(newImage);
        //добавить студенту его аву
//        userService.update(user);
        imageRepository.save(newImage);

    }


    private String getExtensions(String fileName) {
        String extentions = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.debug("extentions is: {}", extentions);
        return extentions;
    }
}