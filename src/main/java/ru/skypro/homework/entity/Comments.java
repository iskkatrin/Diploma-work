package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Comments {
    @Id
    @GeneratedValue
    private long commentsId;
    private int count;
    @OneToMany(mappedBy = "comments")
    private List<Comment> results;
}
