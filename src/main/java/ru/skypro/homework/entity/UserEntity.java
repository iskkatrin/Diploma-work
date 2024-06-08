package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private String password;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> commentEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<AdEntity> adEntity;
}
