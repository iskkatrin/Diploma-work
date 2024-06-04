package ru.skypro.homework.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private Long authorId;
    private String image;
    private Integer price;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    //убрал тк нигде не написано про это поле
    private String description;
}

