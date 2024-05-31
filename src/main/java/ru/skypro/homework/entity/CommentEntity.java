package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comments;


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
    @ManyToOne
    @JoinColumn(name = "Comments_id")
    @JsonIgnore//обезательно иначе вложенность в друг друга будет
    private Comments comments;
}

