package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PatchMapping("/me/{id}")
    @Operation(summary = "Получение фотографии по ее id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })

    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageEntity imageEntity = imageService.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(imageEntity.getMediaType()));
        headers.setContentLength(imageEntity.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(imageEntity.getData());
    }
}