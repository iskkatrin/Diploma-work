package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table

public class Comment {
    @Id
    @GeneratedValue
    private long commentId;
    @ManyToOne
    @JoinColumn(name = "ad_item_id")
    private Ad ad;
    private long adId;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private Integer pk;
    private String text;
}
