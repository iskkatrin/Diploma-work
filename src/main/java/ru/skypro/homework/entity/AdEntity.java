package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_id", nullable = false)
    private Long authorId;
    private UserEntity author;
    private String image;
    private Integer price;
    private String title;
    //убрал тк нигде не написано про это поле
//    private String description;
}

