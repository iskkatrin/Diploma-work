package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;
import org.springframework.security.core.Authentication;


import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    @Autowired
    private AdsService adsService;

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<AdDTO>> getAllAds() {
        List<AdDTO> ads = adsService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    @Operation(summary = "Добавление объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AdDTO> addAd (@RequestParam("properties") CreateOrUpdateAd properties,
                                       @RequestParam("image") MultipartFile image,
                                       Authentication authentication) throws IOException {
        AdDTO ad = adsService.addAd(properties, image, authentication);
        return new ResponseEntity<>(ad, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ExtendedAd.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ExtendedAd> getAd(@PathVariable int id) {
        ExtendedAd ad = adsService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @adsService.isAuthorAd(principal.username, #adId)")

    @Operation(summary = "Удаление объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> removeAd(@PathVariable int id) {
        adsService.removeAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @adsService.isAuthorAd(principal.username, #adId)")
    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AdDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<AdDTO> updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAd ad) {
        AdDTO updatedAd = adsService.updateAd(id, ad);
        return ResponseEntity.ok(updatedAd);
    }

    @PatchMapping("/{id}/image")
    @PreAuthorize(value = "hasRole('ADMIN') or @adsService.isAuthorAd(principal.getEmail(), #adId)")
    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> updateImage(@PathVariable int adId, @RequestParam("image") MultipartFile image) {
        adsService.updateAdImage(adId, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<AdDTO>> getAdsMe() {
        List<AdDTO> ads = adsService.getAdsForLoggedInUser();
        return ResponseEntity.ok(ads);
    }
}

