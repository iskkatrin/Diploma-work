package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {


    public User findUser(Long userId) {
        return null;
    }
}
