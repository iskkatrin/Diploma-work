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
    private Long id;
    private Long authorId;
    private String author;
    private String image;
    private Integer price;
    private String title;
    private String description;

}

