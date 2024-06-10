package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.MyUserPrincipal;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exceptions.NotEditUserPasswordException;
import ru.skypro.homework.exceptions.NotSaveAvatarEx;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ImageService service;

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(ImageService service) {
        this.service = service;
    }

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        try {
            // Здесь заглушка, тк пользователя не получаем(Лола поправить)
            Long userId = 1L;
            userService.updatePassword(userId, newPassword);
            return ResponseEntity.ok().build();
        } catch (NotEditUserPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {

        String name = authentication.getName();
        log.info("user name is: {}", name);

        // Здесь заглушка, тк пользователя не получаем(Лола поправить)
        UserDTO userDTO = userService.getUserDTO(userService.findByUsername(name).get());

        log.info("user email = {}",userDTO.getEmail());
//        userDTO.setEmail("admin.com");
        return ResponseEntity.ok(userDTO);

//        Long userId = 1L;
//        return ResponseEntity.ok(userService.findUserDTO(userId));
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
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal MyUserPrincipal principal) {

     userService.saveAvatar(image,principal.getUser());
        return ResponseEntity.ok().build();
    }
}


