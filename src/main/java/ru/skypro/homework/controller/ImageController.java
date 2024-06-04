//package ru.skypro.homework.controller;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skypro.homework.exceptions.NotSaveAvatarEx;
//import ru.skypro.homework.service.ImageService;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/users")
//public class ImageController {
//
//    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//    }
//
//    @PatchMapping("/me/image")
//    @Operation(summary = "Обновление аватара авторизованного пользователя")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//
//    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image) {
//        Long userId = 1L;
//
//        try {
//            imageService.uploadImage(userId, image);
//        } catch (NotSaveAvatarEx e) {
//            log.warn("NotSaveAvatarException in save image");
//            return ResponseEntity.badRequest().build();
//        } catch (IOException e) {
//            log.warn("IOException in save image");
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//}