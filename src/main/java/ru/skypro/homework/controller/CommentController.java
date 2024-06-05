package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;

import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads/{adId}/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentsDTO> getComments(@PathVariable("adId") int adId) {
        CommentsDTO comments = commentService.getComments(adId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN') or @adsService.isAuthorAd(authentication.getName(), #adId)")
    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentEntity.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentEntity> addComment(@PathVariable("adId") int adId, @RequestBody CreateOrUpdateComment comment) {
        CommentEntity addedCommentEntity = commentService.addComment(adId, comment);
        return ResponseEntity.ok(addedCommentEntity);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @adsService.isAuthorAd(authentication.getName(), #adId)")
    @Operation(summary = "Удаление комментария")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @adsService.isAuthorAd(authentication.getName(), #adId)")
    @Operation(summary = "Обновление комментария")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentEntity.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentEntity> updateComment(@PathVariable("adId") int adId,
                                                       @PathVariable("commentId") int commentId,
                                                       @RequestBody CreateOrUpdateComment comment) {
        CommentEntity updatedCommentEntity = commentService.updateComment(adId, commentId, comment);
        return ResponseEntity.ok(updatedCommentEntity);
    }
}


