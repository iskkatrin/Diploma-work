package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;

import jakarta.persistence.*;

@Service
@Transactional
public class UserService {


    public User findUser(Long userId) {
        return null;
    }
}
