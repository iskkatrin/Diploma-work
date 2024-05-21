package ru.skypro.homework.repository;

import ru.skypro.homework.dto.Register;

public interface AuthRepositry {

    boolean login(String userName, String password);

    boolean register(Register register);
}
