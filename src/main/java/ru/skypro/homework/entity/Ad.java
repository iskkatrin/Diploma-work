package ru.skypro.homework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue
    private long itemId;
    private String itemName;
    private String author;
    private double price;
    private String title;

    @OneToOne
    @JoinColumn(name = "comments_comments_id")
    private Comment comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}

