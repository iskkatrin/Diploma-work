package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.dto.Register;

public interface AuthRepository extends JpaRepository {

    boolean login(String userName, String password);

    boolean register(Register register);
}