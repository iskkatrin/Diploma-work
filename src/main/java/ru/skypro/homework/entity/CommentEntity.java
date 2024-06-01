package ru.skypro.homework.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table

public class CommentEntity {
    @Id
    @GeneratedValue
    private Long commentId;
//    @ManyToOne
//    @JoinColumn(name = "ad_item_id")
//    private Ad ad;   не увидел такого в условиях и изза этого ошибка вылезает

    //такого поля я тоже не увидел в условии
//    private Long adId;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
    private Integer pk;
    private String text;
}

