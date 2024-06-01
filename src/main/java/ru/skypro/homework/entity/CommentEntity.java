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
    private long commentId;
//    @ManyToOne
//    @JoinColumn(name = "ad_item_id")
//    private Ad ad;   не увидел такого в условиях и изза этого ошибка вылезает
    private long adId;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private int pk;
    private String text;
}

