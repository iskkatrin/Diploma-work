package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exceptions.NotSaveAvatarEx;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ImageService service;

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(ImageService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        UserEntity userEntity = null;
        try {
            userEntity = userService.getUserById(id);

        } catch (Exception e) {
            log.info("user is null");
            return null;
        }
        UserDTO userDTO = userService.getUserDTO(userEntity);
        return userDTO;
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.getUser(userDTO);
        userService.saveUser(userEntity);
    }

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"
            )
    })
    public ResponseEntity<UserDTO> getUser() {
        UserDTO userDTO = new UserDTO();
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        //заглушка тк не знаем какой пользователь(Лола поправить)
        Long userId = 1L;
        return ResponseEntity.ok(userService.updateUser(userId, updateUser));
    }

    @PatchMapping("/me/image")
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) {
        //заглушка тк не получаем пользователя(Лола исправить)
        Long userId = 1L;
        try {
            service.uploadImage(userId, image);
        } catch (NotSaveAvatarEx e) {
            log.warn("NotSaveAvatarException in save image");
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.warn("IOException in save image");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}


