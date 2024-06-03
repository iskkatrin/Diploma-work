package ru.skypro.homework.entity;

import jakarta.persistence.*;
import lombok.*;


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
    @JoinColumn(name = "image_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ImageEntity imageEntity;
    private Integer price;
    private String title;
    //убрал тк нигде не написано про это поле
    private String description;
}

