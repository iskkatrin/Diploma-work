package ru.skypro.homework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class AdEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long authorId;
    private String image;
    private Integer price;
    private String title;
    //убрал тк нигде не написано про это поле
//    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

}

