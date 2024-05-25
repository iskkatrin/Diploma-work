package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Ad {
    @Id
    @GeneratedValue
    private long itemId;
    private String itemName;
    private String author;
    private String image;
    private double price;
    private String title;

    @OneToOne
    @JoinColumn(name = "comments_comments_id")
    private Comments comments;
}
