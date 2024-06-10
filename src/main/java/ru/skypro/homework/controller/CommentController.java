package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;

import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentsDTO> getComments(@PathVariable("id") long id) {
        CommentsDTO comments = commentService.getComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping(value = "/{id}/comments")
//    @PreAuthorize("hasRole('ADMIN') or @adsService.isAuthorAd(#principal.username, #adId)")
    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDTO> addComment(Authentication authentication,
                                                 @PathVariable("id") int id,
                                                 @RequestBody CreateOrUpdateComment comment
                                                 /*Authentication authentication*/) {

//        log.info("user name in commentController is {}",authentication.getName());
        CommentDTO addedCommentDTO = commentService.addComment(authentication, id, comment);
        log.info("comment dto in commentController is {}", addedCommentDTO);
        return ResponseEntity.ok(addedCommentDTO);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
//    @PreAuthorize("hasRole('ADMIN') or @adsService.isAuthorAd(#principal.username, #adId)")
    @Operation(summary = "Удаление комментария")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") int adId, @PathVariable("commentId") long commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
//    @PreAuthorize("hasRole('ADMIN') or @adsService.isAuthorAd(#principal.username, #adId)")
    @Operation(summary = "Обновление комментария")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("adId") long adId,
                                                       @PathVariable("commentId") long commentId,
                                                       @RequestBody CreateOrUpdateComment comment) {
        CommentDTO updatedCommentDTO = commentService.updateComment(adId, commentId, comment);
        return ResponseEntity.ok(updatedCommentDTO);
    }
}


