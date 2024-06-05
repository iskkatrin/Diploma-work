package ru.skypro.homework.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class AdEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_id", nullable = false)
    private Long authorId;
    private UserEntity author;
    private String image;
    private Integer price;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    @OneToMany(mappedBy = "adEntity")
    private List<CommentEntity> commentEntity;

    private String description;
}

