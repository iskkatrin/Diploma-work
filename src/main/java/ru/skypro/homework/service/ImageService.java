package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;


@Service
@Transactional
public class ImageService {

    @Value("${avatar.image.directory}")
    private String avatarsDir;



    public void uploadImage(Long userId, MultipartFile image) {
        return;
    }
}